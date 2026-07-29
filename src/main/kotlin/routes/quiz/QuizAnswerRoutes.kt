package com.fathersprophets.backend.routes.quiz

import com.fathersprophets.backend.models.quizanswer.CreateQuizAnswerRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.quiz.quizanswer.IQuizAnswerService
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.quizAnswerRoutes(service: IQuizAnswerService) {
    route("/quiz-answers") {

        get {
            call.requireRole("superadmin", "admin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllQuizAnswers(lang))
        }

        get("/my-answers/{dayId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("userId")?.asInt()
            val dayId = call.parameters["dayId"]?.toIntOrNull()
            call.respond(service.getQuizAnswersByUserIdAndDayId(userId, dayId, lang))
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

        delete("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteQuizAnswer(id, lang))
        }
    }
}