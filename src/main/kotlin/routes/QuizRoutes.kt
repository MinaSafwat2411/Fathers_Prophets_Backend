package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.quiz.CreateQuizRequest
import com.fathersprophets.backend.models.quiz.UpdateQuizRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.quiz.IQuizService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.quizRoutes(service: IQuizService) {
    route("/quiz") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllQuizzes(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.getQuizById(id, lang))
        }

        post {
            call.requireRole("admin", "superadmin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateQuizRequest>()
            call.respond(service.createQuiz(request, lang))
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateQuizRequest>()
            call.respond(service.updateQuiz(id, request, lang))
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteQuiz(id, lang))
        }
    }
}