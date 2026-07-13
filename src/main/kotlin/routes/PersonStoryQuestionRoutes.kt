package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.personstoryquestion.CreatePersonStoryQuestionRequest
import com.fathersprophets.backend.models.personstoryquestion.UpdatePersonStoryQuestionRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.person.personstory.personstoryquestion.IPersonStoryQuestionService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.personStoryQuestionRoutes(personStoryQuestionService: IPersonStoryQuestionService) {
    route("/person-story-question") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = personStoryQuestionService.getAllPersonStoryQuestions(lang)
            call.respond(response)
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personStoryQuestionService.getPersonStoryQuestionById(id, lang)
            call.respond(response)
        }

        get("/story/{storyId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val storyId = call.parameters["storyId"]?.toIntOrNull()
            val response = personStoryQuestionService.getPersonStoryQuestionsByStoryId(storyId, lang)
            call.respond(response)
        }

        post {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreatePersonStoryQuestionRequest>()
            val response = personStoryQuestionService.createPersonStoryQuestion(request, lang)
            call.respond(response)
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdatePersonStoryQuestionRequest>()
            val response = personStoryQuestionService.updatePersonStoryQuestion(id, request, lang)
            call.respond(response)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personStoryQuestionService.deletePersonStoryQuestion(id, lang)
            call.respond(response)
        }
    }
}