package com.fathersprophets.backend.routes.quiz

import com.fathersprophets.backend.models.quizdayquestion.CreateQuizDayQuestionRequest
import com.fathersprophets.backend.models.quizdayquestion.UpdateQuizDayQuestionRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.quiz.quizdayquestion.IQuizDayQuestionService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.quizDayQuestionRoutes(service: IQuizDayQuestionService) {
    route("/quiz-day-questions") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllQuestions(lang))
        }

        get("/quiz-day/{quizDayId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val quizDayId = call.parameters["quizDayId"]?.toIntOrNull()
            call.respond(service.getQuestionsByQuizDayId(quizDayId, lang))
        }

        post {
            call.requireRole("admin", "superadmin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateQuizDayQuestionRequest>()
            call.respond(service.createQuestion(request, lang))
        }

        post("/bulk") {
            call.requireRole("admin", "superadmin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val requests = call.receive<List<CreateQuizDayQuestionRequest>>()
            call.respond(service.createQuestions(requests, lang))
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateQuizDayQuestionRequest>()
            call.respond(service.updateQuestion(id, request, lang))
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteQuestion(id, lang))
        }
    }
}