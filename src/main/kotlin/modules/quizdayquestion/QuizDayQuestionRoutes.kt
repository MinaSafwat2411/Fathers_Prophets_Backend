package com.fathersprophets.backend.modules.quizdayquestion

import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.modules.quizdayquestion.service.IQuizDayQuestionService
import com.fathersprophets.backend.utils.Localization
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.quizDayQuestionRoutes(quizDayQuestionService: IQuizDayQuestionService) {
    route("/quiz-day-questions") {
        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(quizDayQuestionService.getAll(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(quizDayQuestionService.getById(id, lang))
        }

        get("/quiz-day/{quizDayId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val quizDayId = call.parameters["quizDayId"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(quizDayQuestionService.getByQuizDayId(quizDayId, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<QuizDayQuestionCreateDto>()
            call.respond(quizDayQuestionService.create(request, lang))
        }

        // Questions are authored a day at a time, so the whole set can be submitted in one call.
        post("/bulk") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<List<QuizDayQuestionCreateDto>>()
            call.respond(quizDayQuestionService.createAll(request, lang))
        }

        put("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            val request = call.receive<QuizDayQuestionUpdateDto>()
            call.respond(quizDayQuestionService.update(id, request, lang))
        }

        delete("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(quizDayQuestionService.delete(id, lang))
        }
    }
}