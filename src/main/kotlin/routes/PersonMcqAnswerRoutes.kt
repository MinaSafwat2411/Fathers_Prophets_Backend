package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.personmcqanswer.CreatePersonMcqAnswerRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.person.mcq.personmcqanswer.IPersonMcqAnswerService
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.personMcqAnswerRoutes(personMcqAnswerService: IPersonMcqAnswerService) {
    route("/person-mcq-answer") {

        get {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = personMcqAnswerService.getAllPersonMcqAnswers(lang)
            call.respond(response)
        }

        get("/question/{questionId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("id")?.asInt()
            val questionId = call.parameters["questionId"]?.toIntOrNull()
            val response = personMcqAnswerService.getPersonMcqAnswersByUserIdAndQuestionId(questionId, userId, lang)
            call.respond(response)
        }


        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreatePersonMcqAnswerRequest>()
            val response = personMcqAnswerService.createPersonMcqAnswer(request, lang)
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