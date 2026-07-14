package com.fathersprophets.backend.routes.activity.timeline

import com.fathersprophets.backend.models.timelineanswer.CreateTimelineAnswerRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.activity.timeline.timelineanswer.ITimelineAnswerService
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.timelineAnswerRoutes(service: ITimelineAnswerService) {
    route("/timeline-answers") {

        get {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllAnswers(lang))
        }

        get("/my-answers") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val payload = call.principal<JWTPrincipal>()
            val userId = payload?.payload?.getClaim("userId")?.asInt()
            call.respond(service.getAnswersByUserId(userId, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateTimelineAnswerRequest>()
            call.respond(service.createAnswer(request, lang))
        }


        delete("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteAnswer(id, lang))
        }
    }
}