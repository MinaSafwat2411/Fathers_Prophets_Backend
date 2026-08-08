package com.fathersprophets.backend.modules.quiz

import com.fathersprophets.backend.database.tables.quiz.QuizCreateDto
import com.fathersprophets.backend.database.tables.quiz.QuizUpdateDto
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.modules.quiz.service.IQuizService
import com.fathersprophets.backend.utils.Localization
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.quizRoutes(quizService: IQuizService) {
    route("/quizzes") {
        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(quizService.getAll(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(quizService.getById(id, lang))
        }

        get("/number/{number}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val number = call.parameters["number"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(quizService.getByNumber(number, lang))
        }

        get("/family/{familyId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val familyId = call.parameters["familyId"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(quizService.getByFamilyId(familyId, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<QuizCreateDto>()
            call.respond(quizService.create(request, lang))
        }

        put("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            val request = call.receive<QuizUpdateDto>()
            call.respond(quizService.update(id, request, lang))
        }

        delete("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(quizService.delete(id, lang))
        }
    }
}