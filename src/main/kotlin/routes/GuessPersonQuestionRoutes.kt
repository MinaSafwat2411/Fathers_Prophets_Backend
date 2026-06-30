package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.guessperson.CreateGuessPersonQuestionRequest
import com.fathersprophets.backend.models.guessperson.UpdateGuessPersonQuestionRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.guessperson.IGuessPersonQuestionService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.guessPersonQuestionRoutes(service: IGuessPersonQuestionService) {
    route("/guess-person-questions") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = service.getAllQuestions(lang)
            call.respond(response)
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = service.getQuestionById(id, lang)
            call.respond(response)
        }

        post {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateGuessPersonQuestionRequest>()
            val response = service.createQuestion(request, lang)
            call.respond(response)
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateGuessPersonQuestionRequest>()
            val response = service.updateQuestion(id, request, lang)
            call.respond(response)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = service.deleteQuestion(id, lang)
            call.respond(response)
        }
    }
}