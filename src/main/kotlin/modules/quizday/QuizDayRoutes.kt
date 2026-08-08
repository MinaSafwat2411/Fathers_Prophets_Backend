package com.fathersprophets.backend.modules.quizday

import com.fathersprophets.backend.database.enums.DayOfWeek
import com.fathersprophets.backend.database.tables.quizday.QuizDayCreateDto
import com.fathersprophets.backend.database.tables.quizday.QuizDayUpdateDto
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.modules.quizday.service.IQuizDayService
import com.fathersprophets.backend.utils.Localization
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.quizDayRoutes(quizDayService: IQuizDayService) {
    route("/quiz-days") {
        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(quizDayService.getAll(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(quizDayService.getById(id, lang))
        }

        get("/quiz/{quizId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val quizId = call.parameters["quizId"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(quizDayService.getByQuizId(quizId, lang))
        }

        get("/quiz/{quizId}/day/{dayName}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val quizId = call.parameters["quizId"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            val dayName = DayOfWeek.entries.firstOrNull { it.name.equals(call.parameters["dayName"], ignoreCase = true) }
                ?: throw BadRequestException(Localization.get("invalid_day_name", lang))
            call.respond(quizDayService.getByQuizAndDay(quizId, dayName, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<QuizDayCreateDto>()
            call.respond(quizDayService.create(request, lang))
        }

        put("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            val request = call.receive<QuizDayUpdateDto>()
            call.respond(quizDayService.update(id, request, lang))
        }

        delete("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(quizDayService.delete(id, lang))
        }
    }
}