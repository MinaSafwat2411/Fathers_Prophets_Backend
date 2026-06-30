package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.guesspersonanswer.CreateGuessPersonAnswerRequest
import com.fathersprophets.backend.models.guesspersonanswer.UpdateGuessPersonAnswerRequest
import com.fathersprophets.backend.models.guesspersonanswer.UpdateGuessPersonAnswerStatusRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.guesspersonanswer.IGuessPersonAnswerService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.guessPersonAnswerRoutes(service: IGuessPersonAnswerService) {
    route("/guess-person-answers") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllAnswers(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.getAnswerById(id, lang))
        }

        get("/question/{questionId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val questionId = call.parameters["questionId"]?.toIntOrNull()
            call.respond(service.getAnswersByQuestionId(questionId, lang))
        }

        get("/user/{userId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val userId = call.parameters["userId"]?.toIntOrNull()
            call.respond(service.getAnswersByUserId(userId, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateGuessPersonAnswerRequest>()
            call.respond(service.createAnswer(request, lang))
        }

        put("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateGuessPersonAnswerRequest>()
            call.respond(service.updateAnswer(id, request, lang))
        }

        patch("/{id}/status") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateGuessPersonAnswerStatusRequest>()
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