package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.userprogressquiz.CreateUserProgressQuizRequest
import com.fathersprophets.backend.models.userprogressquiz.UpdateUserProgressQuizRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.users.userprogressquiz.IUserProgressQuizService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userProgressQuizRoutes(service: IUserProgressQuizService) {
    route("/user-progress-quiz") {

        get {
            call.requireRole("superadmin", "admin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllUserProgress(lang))
        }

        get("/{id}") {
            call.requireRole("superadmin", "admin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.getUserProgressById(id, lang))
        }

        get("/user/{userId}") {
            call.requireRole("superadmin", "admin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val userId = call.parameters["userId"]?.toIntOrNull()
            call.respond(service.getUserProgressByUserId(userId, lang))
        }

        get("/quiz/{quizId}") {
            call.requireRole("superadmin", "admin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val quizId = call.parameters["quizId"]?.toIntOrNull()
            call.respond(service.getUserProgressByQuizId(quizId, lang))
        }

        get("/user/{userId}/quiz/{quizId}/day/{dayId}") {
            call.requireRole("superadmin", "admin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val userId = call.parameters["userId"]?.toIntOrNull()
            val quizId = call.parameters["quizId"]?.toIntOrNull()
            val dayId = call.parameters["dayId"]?.toIntOrNull()
            call.respond(service.getUserProgressByUserIdAndQuizIdAndDayId(userId, quizId, dayId, lang))
        }

        post {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateUserProgressQuizRequest>()
            call.respond(service.createUserProgress(request, lang))
        }

        put("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateUserProgressQuizRequest>()
            call.respond(service.updateUserProgress(id, request, lang))
        }

        delete("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteUserProgress(id, lang))
        }
    }
}