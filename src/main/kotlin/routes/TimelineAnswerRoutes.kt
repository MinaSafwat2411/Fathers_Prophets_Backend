package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.timelineanswer.CreateTimelineAnswerRequest
import com.fathersprophets.backend.models.timelineanswer.UpdateTimelineAnswerRequest
import com.fathersprophets.backend.models.timelineanswer.UpdateTimelineAnswerStatusRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.activity.timeline.timelineanswer.ITimelineAnswerService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.timelineAnswerRoutes(service: ITimelineAnswerService) {
    route("/timeline-answers") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllAnswers(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.getAnswerById(id, lang))
        }

        get("/timeline/{timelineId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val timelineId = call.parameters["timelineId"]?.toIntOrNull()
            call.respond(service.getAnswersByTimelineId(timelineId, lang))
        }

        get("/user/{userId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val userId = call.parameters["userId"]?.toIntOrNull()
            call.respond(service.getAnswersByUserId(userId, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateTimelineAnswerRequest>()
            call.respond(service.createAnswer(request, lang))
        }

        put("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateTimelineAnswerRequest>()
            call.respond(service.updateAnswer(id, request, lang))
        }

        patch("/{id}/status") {
            call.requireRole("superadmin", "admin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateTimelineAnswerStatusRequest>()
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