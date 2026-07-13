package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.personstoryanswer.CreatePersonStoryAnswerRequest
import com.fathersprophets.backend.models.personstoryanswer.UpdatePersonStoryAnswerRequest
import com.fathersprophets.backend.models.personstoryanswer.UpdatePersonStoryAnswerStatusRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.person.personstory.personstoryanswer.IPersonStoryAnswerService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.personStoryAnswerRoutes(personStoryAnswerService: IPersonStoryAnswerService) {
    route("/person-story-answer") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = personStoryAnswerService.getAllPersonStoryAnswers(lang)
            call.respond(response)
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personStoryAnswerService.getPersonStoryAnswerById(id, lang)
            call.respond(response)
        }

        get("/story/{storyId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val storyId = call.parameters["storyId"]?.toIntOrNull()
            val response = personStoryAnswerService.getPersonStoryAnswersByStoryId(storyId, lang)
            call.respond(response)
        }

        get("/user/{userId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val userId = call.parameters["userId"]?.toIntOrNull()
            val response = personStoryAnswerService.getPersonStoryAnswersByUserId(userId, lang)
            call.respond(response)
        }

        get("/question/{questionId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val questionId = call.parameters["questionId"]?.toIntOrNull()
            val response = personStoryAnswerService.getPersonStoryAnswersByQuestionId(questionId, lang)
            call.respond(response)
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreatePersonStoryAnswerRequest>()
            val response = personStoryAnswerService.createPersonStoryAnswer(request, lang)
            call.respond(response)
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdatePersonStoryAnswerRequest>()
            val response = personStoryAnswerService.updatePersonStoryAnswer(id, request, lang)
            call.respond(response)
        }

        patch("/{id}/status") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdatePersonStoryAnswerStatusRequest>()
            val response = personStoryAnswerService.updatePersonStoryAnswerStatus(id, request, lang)
            call.respond(response)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personStoryAnswerService.deletePersonStoryAnswer(id, lang)
            call.respond(response)
        }
    }
}