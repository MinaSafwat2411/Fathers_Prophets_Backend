package com.fathersprophets.backend.routes.activity.escapeegypt

import com.fathersprophets.backend.models.escapeegyptanswer.CreateEscapeEgyptAnswerRequest
import com.fathersprophets.backend.models.escapeegyptanswer.UpdateEscapeEgyptAnswerRequest
import com.fathersprophets.backend.models.escapeegyptanswer.UpdateEscapeEgyptAnswerStatusRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.activity.escapeegypt.escapeegyptanswer.IEscapeEgyptAnswerService
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.escapeEgyptAnswerRoutes(service: IEscapeEgyptAnswerService) {
    route("/escape-egypt-answers") {

        get {
            call.requireRole("superadmin","admin","games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllAnswers(lang))
        }

        get("/escape-egypt/{escapeEgyptId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val escapeEgyptId = call.parameters["escapeEgyptId"]?.toIntOrNull()
            call.respond(service.getAnswersByEscapeEgyptId(escapeEgyptId, lang))
        }

        get("/my-answers/{escapeEgyptId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val escapeEgyptId = call.parameters["escapeEgyptId"]?.toIntOrNull()
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("userId")?.asInt()
            call.respond(service.getAnswersByUserIdAndEscapeEgyptId(userId, escapeEgyptId, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateEscapeEgyptAnswerRequest>()
            call.respond(service.createAnswer(request, lang))
        }

        put("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateEscapeEgyptAnswerRequest>()
            call.respond(service.updateAnswer(id, request, lang))
        }

        patch("/{id}/status") {
            call.requireRole("superadmin","admin","games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateEscapeEgyptAnswerStatusRequest>()
            call.respond(service.updateAnswerStatus(id, request, lang))
        }

        delete("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteAnswer(id, lang))
        }
    }
}