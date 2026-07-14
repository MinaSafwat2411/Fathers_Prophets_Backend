package com.fathersprophets.backend.routes.attendance

import com.fathersprophets.backend.models.attendance.AddAttendanceRequest
import com.fathersprophets.backend.models.attendance.UpdateAttendanceRequest
import com.fathersprophets.backend.plugins.forbidRoles
import com.fathersprophets.backend.services.attendance.IAttendanceService
import com.fathersprophets.backend.utils.AttendanceEventBroadcaster
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun Route.attendanceRoutes(attendanceService: IAttendanceService) {
    route("/attendance") {
        post {
            call.forbidRoles("member")
            val lang = call.request.header("Accept-Language") ?: "en"
            val request = call.receive<AddAttendanceRequest>()
            val response = attendanceService.addAttendance(request, lang)
            call.respond(if (response.success) HttpStatusCode.Created else HttpStatusCode.BadRequest, response)
        }

        get {
            call.forbidRoles("member")

            val lang = call.request.header("Accept-Language") ?: "en"
            val response = attendanceService.getAllAttendance(lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }

        webSocket("/session/{sessionId}/{classId}") {
            call.forbidRoles("member")

            try {
                val sessionId = call.parameters["sessionId"]?.toIntOrNull()
                val classId = call.parameters["classId"]?.toIntOrNull()
                val lang = call.request.header("Accept-Language") ?: "en"

                if (sessionId == null || classId == null) {
                    close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, "Session ID or Class ID is required"))
                    return@webSocket
                }

                val initialResponse = attendanceService.getAttendanceByClassIdAndSessionId(classId, sessionId, lang)
                sendSerialized(initialResponse)
                val job = launch {
                    AttendanceEventBroadcaster.attendanceEvents.collectLatest { (broadcastSessionId, attendanceResponse) ->
                        if (broadcastSessionId == sessionId) {
                            sendSerialized(attendanceResponse)
                        }
                    }
                }

                for (frame in incoming) {
                    if (frame is Frame.Close) {
                        job.cancel()
                        close(CloseReason(CloseReason.Codes.NORMAL, "Client disconnected"))
                        break
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
                close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, e.message ?: "Unknown error"))
            }
        }

        webSocket("/session/{sessionId}") {
            call.forbidRoles("member")

            try {
                val sessionId = call.parameters["sessionId"]?.toIntOrNull()
                val lang = call.request.header("Accept-Language") ?: "en"

                if (sessionId == null) {
                    close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, "Session ID is required"))
                    return@webSocket
                }

                val initialResponse = attendanceService.getAttendanceBySessionId(sessionId, lang)
                sendSerialized(initialResponse)

                val job = launch {
                    AttendanceEventBroadcaster.attendanceEvents.collectLatest { (broadcastSessionId, attendanceResponse) ->
                        if (broadcastSessionId == sessionId) {
                            sendSerialized(attendanceResponse)
                        }
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
            call.forbidRoles("member")
            val id = call.parameters["id"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val response = attendanceService.deleteAttendance(id, lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }
    }
}
