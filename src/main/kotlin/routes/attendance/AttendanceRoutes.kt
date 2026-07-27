package com.fathersprophets.backend.routes.attendance

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.attendance.AddAttendanceRequest
import com.fathersprophets.backend.models.attendance.AttendanceResponse
import com.fathersprophets.backend.models.attendance.UpdateAttendanceRequest
import com.fathersprophets.backend.plugins.forbidRoles
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.attendance.IAttendanceService
import com.fathersprophets.backend.utils.AttendanceEventBroadcaster
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private suspend fun DefaultWebSocketServerSession.streamAttendance(
    initial: suspend () -> ApiResponse<List<AttendanceResponse>>,
    onBroadcast: suspend (sessionId: Int, response: ApiResponse<List<AttendanceResponse>>) -> Unit
) {
    try {
        sendSerialized(initial())
        val job = launch {
            AttendanceEventBroadcaster.attendanceEvents.collectLatest { (broadcastSessionId, attendanceResponse) ->
                onBroadcast(broadcastSessionId, attendanceResponse)
            }
        }

        for (frame in incoming) {
            if (frame is Frame.Close) {
                job.cancel()
                close(CloseReason(CloseReason.Codes.NORMAL, "Client disconnected"))
                break
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, e.message ?: "Unknown error"))
    }
}

fun Route.attendanceRoutes(attendanceService: IAttendanceService) {
    route("/attendance") {
        post {
            call.forbidRoles("member")
            val lang = call.request.header("Accept-Language") ?: "en"
            val request = call.receive<AddAttendanceRequest>()
            val response = attendanceService.addAttendance(request, lang)
            call.respond(if (response.success) HttpStatusCode.Created else HttpStatusCode.BadRequest, response)
        }

        webSocket("/all") {
            call.requireRole("admin", "superadmin")

            val lang = call.request.header("Accept-Language") ?: "en"

            streamAttendance(
                initial = { attendanceService.getAllAttendance(lang) },
                onBroadcast = { _, _ -> sendSerialized(attendanceService.getAllAttendance(lang)) }
            )
        }

        webSocket("/session/{sessionId}") {
            call.requireRole("admin", "superadmin")

            val sessionId = call.parameters["sessionId"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"

            if (sessionId == null) {
                close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, "Session ID or Class ID is required"))
                return@webSocket
            }

            streamAttendance(
                initial = { attendanceService.getAttendanceBySessionId(sessionId, lang) },
                onBroadcast = { broadcastSessionId, response ->
                    if (broadcastSessionId == sessionId) sendSerialized(response)
                }
            )
        }

        webSocket("/session/my-class/{sessionId}") {
            call.forbidRoles("member")

            val sessionId = call.parameters["sessionId"]?.toIntOrNull()
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("userId")?.asInt()
            val lang = call.request.header("Accept-Language") ?: "en"

            if (sessionId == null) {
                close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, "Session ID or Class ID is required"))
                return@webSocket
            }

            streamAttendance(
                initial = { attendanceService.getAttendanceByClassIdAndSessionId(userId, sessionId, lang) },
                onBroadcast = { broadcastSessionId, response ->
                    if (broadcastSessionId == sessionId) sendSerialized(response)
                }
            )
        }

        get("/member/{memberId}") {
            call.forbidRoles("member")

            val memberId = call.parameters["memberId"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val response = attendanceService.getAttendanceByUserId(memberId, lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }

        get("/class/{classId}") {
            call.forbidRoles("member")

            val classId = call.parameters["classId"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val response = attendanceService.getAttendanceByClassId(classId, lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }

        put("/{id}") {

            call.forbidRoles("member")
            val id = call.parameters["id"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val request = call.receive<UpdateAttendanceRequest>()
            val response = attendanceService.updateAttendance(id, request, lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin")
            val id = call.parameters["id"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val response = attendanceService.deleteAttendance(id, lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }
    }
}
