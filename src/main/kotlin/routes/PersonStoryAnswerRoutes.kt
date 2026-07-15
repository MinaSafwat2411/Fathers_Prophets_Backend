package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.personstoryanswer.CreatePersonStoryAnswerRequest
import com.fathersprophets.backend.models.personstoryanswer.UpdatePersonStoryAnswerRequest
import com.fathersprophets.backend.models.personstoryanswer.UpdatePersonStoryAnswerStatusRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.person.personstory.personstoryanswer.IPersonStoryAnswerService
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.personStoryAnswerRoutes(personStoryAnswerService: IPersonStoryAnswerService) {
    route("/person-story-answer") {

        get {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = personStoryAnswerService.getAllPersonStoryAnswers(lang)
            call.respond(response)
        }

        get("/story/{storyId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("id")?.asInt()
            val storyId = call.parameters["storyId"]?.toIntOrNull()
            val response = personStoryAnswerService.getPersonStoryAnswersByUserAndStoryId(storyId,userId, lang)
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