package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.personmcqanswer.CreatePersonMcqAnswerRequest
import com.fathersprophets.backend.models.personmcqanswer.UpdateMcqAnswerStatusRequest
import com.fathersprophets.backend.models.personmcqanswer.UpdatePersonMcqAnswerRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.person.mcq.personmcqanswer.IPersonMcqAnswerService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.personMcqAnswerRoutes(personMcqAnswerService: IPersonMcqAnswerService) {
    route("/person-mcq-answer") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = personMcqAnswerService.getAllPersonMcqAnswers(lang)
            call.respond(response)
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personMcqAnswerService.getPersonMcqAnswerById(id, lang)
            call.respond(response)
        }

        get("/question/{questionId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val questionId = call.parameters["questionId"]?.toIntOrNull()
            val response = personMcqAnswerService.getPersonMcqAnswersByQuestionId(questionId, lang)
            call.respond(response)
        }

        get("/user/{userId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val userId = call.parameters["userId"]?.toIntOrNull()
            val response = personMcqAnswerService.getPersonMcqAnswersByUserId(userId, lang)
            call.respond(response)
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreatePersonMcqAnswerRequest>()
            val response = personMcqAnswerService.createPersonMcqAnswer(request, lang)
            call.respond(response)
        }

        put("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdatePersonMcqAnswerRequest>()
            val response = personMcqAnswerService.updatePersonMcqAnswer(id, request, lang)
            call.respond(response)
        }

        patch("/{id}/status") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateMcqAnswerStatusRequest>()
            val response = personMcqAnswerService.updatePersonMcqAnswerStatus(id, request, lang)
            call.respond(response)
        }

        delete("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personMcqAnswerService.deletePersonMcqAnswer(id, lang)
            call.respond(response)
        }
    }
}