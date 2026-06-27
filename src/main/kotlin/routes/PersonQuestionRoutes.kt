package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.personquestion.CreateQuestionRequest
import com.fathersprophets.backend.models.personquestion.UpdateQuestionRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.personquestion.IPersonQuestionService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.personQuestionRoutes(personQuestionService: IPersonQuestionService) {
    route("/person-question") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = personQuestionService.getAllPersonQuestions(lang)
            call.respond(response)
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personQuestionService.getPersonQuestionById(id, lang)
            call.respond(response)
        }

        get("/person/{personId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val personId = call.parameters["personId"]?.toIntOrNull()
            val response = personQuestionService.getPersonQuestionsByPersonId(personId, lang)
            call.respond(response)
        }

        post {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateQuestionRequest>()
            val response = personQuestionService.createPersonQuestion(request, lang)
            call.respond(response)
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateQuestionRequest>()
            val response = personQuestionService.updatePersonQuestion(id, request, lang)
            call.respond(response)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personQuestionService.deletePersonQuestion(id, lang)
            call.respond(response)
        }
    }
}