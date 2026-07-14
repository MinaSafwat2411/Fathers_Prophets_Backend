package com.fathersprophets.backend.routes.attendance

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.session.AddSessionRequest
import com.fathersprophets.backend.models.session.SessionResponse
import com.fathersprophets.backend.models.session.UpdateSessionRequest
import com.fathersprophets.backend.plugins.forbidRoles
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.attendance.session.ISessionService
import com.fathersprophets.backend.utils.SessionEventBroadcaster
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private suspend fun DefaultWebSocketServerSession.streamSessions(
    initial: suspend () -> ApiResponse<List<SessionResponse>>
) {
    try {
        sendSerialized(initial())
        val job = launch {
            SessionEventBroadcaster.sessionEvents.collectLatest { response ->
                sendSerialized(response)
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

fun Route.sessionRoutes(sessionService: ISessionService) {
    route("/sessions") {
        post {
            call.requireRole("admin", "superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<AddSessionRequest>()
            val response = sessionService.createSession(request, lang)
            call.respond(if (response.success) HttpStatusCode.Created else HttpStatusCode.BadRequest, response)
        }

        webSocket {
            call.forbidRoles("member")

            streamSessions { sessionService.getAllSessions() }
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateSessionRequest>()
            val response = sessionService.updateSession(id, request, lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = sessionService.deleteSession(id, lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }
    }
}
