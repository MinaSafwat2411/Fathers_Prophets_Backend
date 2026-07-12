package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.personanswer.CreatePersonAnswerRequest
import com.fathersprophets.backend.models.personanswer.UpdateAnswerStatusRequest
import com.fathersprophets.backend.models.personanswer.UpdatePersonAnswerRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.person.complete.personanswer.IPersonAnswerService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.personAnswerRoutes(personAnswerService: IPersonAnswerService) {
    route("/person-answer") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = personAnswerService.getAllPersonAnswers(lang)
            call.respond(response)
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personAnswerService.getPersonAnswerById(id, lang)
            call.respond(response)
        }

        get("/question/{questionId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val questionId = call.parameters["questionId"]?.toIntOrNull()
            val response = personAnswerService.getPersonAnswersByQuestionId(questionId, lang)
            call.respond(response)
        }

        get("/user/{userId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val userId = call.parameters["userId"]?.toIntOrNull()
            val response = personAnswerService.getPersonAnswersByUserId(userId, lang)
            call.respond(response)
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreatePersonAnswerRequest>()
            val response = personAnswerService.createPersonAnswer(request, lang)
            call.respond(response)
        }

        put("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdatePersonAnswerRequest>()
            val response = personAnswerService.updatePersonAnswer(id, request, lang)
            call.respond(response)
        }

        patch("/{id}/status") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateAnswerStatusRequest>()
            val response = personAnswerService.updatePersonAnswerStatus(id, request, lang)
            call.respond(response)
        }

        delete("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personAnswerService.deletePersonAnswer(id, lang)
            call.respond(response)
        }
    }
}