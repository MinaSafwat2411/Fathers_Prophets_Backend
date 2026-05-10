package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.session.AddSessionRequest
import com.fathersprophets.backend.models.session.UpdateSessionRequest
import com.fathersprophets.backend.plugins.forbidRoles
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.session.ISessionService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.sessionRoutes(sessionService: ISessionService) {
    route("/sessions") {
        post {
            call.requireRole("admin", "superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<AddSessionRequest>()
            val response = sessionService.createSession(request, lang)
            call.respond(if (response.success) HttpStatusCode.Created else HttpStatusCode.BadRequest, response)
        }

        get {
            call.forbidRoles("member")

            val response = sessionService.getAllSessions()
            call.respond(HttpStatusCode.OK, response)
        }

        get("/{id}") {
            call.forbidRoles("member")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = sessionService.getSessionById(id, lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.NotFound, response)
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
