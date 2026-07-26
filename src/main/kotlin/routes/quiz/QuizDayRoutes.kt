package com.fathersprophets.backend.routes.quiz

import com.fathersprophets.backend.models.quizday.CreateQuizDayRequest
import com.fathersprophets.backend.models.quizday.UpdateQuizDayRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.quiz.quizday.IQuizDayService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.quizDayRoutes(service: IQuizDayService) {
    route("/quiz-day") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllQuizDays(lang))
        }

        get("/quiz/{quizId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val quizId = call.parameters["quizId"]?.toIntOrNull()
            call.respond(service.getQuizDaysByQuizId(quizId, lang))
        }

        post {
            call.requireRole("admin", "superadmin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateQuizDayRequest>()
            call.respond(service.createQuizDay(request, lang))
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateQuizDayRequest>()
            call.respond(service.updateQuizDay(id, request, lang))
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteQuizDay(id, lang))
        }
    }
}