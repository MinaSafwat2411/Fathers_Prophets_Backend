package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.quizanswer.CreateQuizAnswerRequest
import com.fathersprophets.backend.models.quizanswer.UpdateQuizAnswerRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.quizanswer.IQuizAnswerService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.quizAnswerRoutes(service: IQuizAnswerService) {
    route("/quiz-answers") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllQuizAnswers(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.getQuizAnswerById(id, lang))
        }

        get("/question/{questionId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val questionId = call.parameters["questionId"]?.toIntOrNull()
            call.respond(service.getQuizAnswersByQuestionId(questionId, lang))
        }

        get("/user/{userId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val userId = call.parameters["userId"]?.toIntOrNull()
            call.respond(service.getQuizAnswersByUserId(userId, lang))
        }

        get("/day/{dayId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val dayId = call.parameters["dayId"]?.toIntOrNull()
            call.respond(service.getQuizAnswersByDayId(dayId, lang))
        }

        get("/quiz/{quizId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val quizId = call.parameters["quizId"]?.toIntOrNull()
            call.respond(service.getQuizAnswersByQuizId(quizId, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateQuizAnswerRequest>()
            call.respond(service.createQuizAnswer(request, lang))
        }

        post("/bulk") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val requests = call.receive<List<CreateQuizAnswerRequest>>()
            call.respond(service.createQuizAnswers(requests, lang))
        }

        put("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateQuizAnswerRequest>()
            call.respond(service.updateQuizAnswer(id, request, lang))
        }

        delete("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteQuizAnswer(id, lang))
        }
    }
}