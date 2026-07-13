package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.personstory.CreatePersonStoryRequest
import com.fathersprophets.backend.models.personstory.UpdatePersonStoryRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.person.personstory.IPersonStoryService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.personStoryRoutes(personStoryService: IPersonStoryService) {
    route("/person-story") {
        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = personStoryService.getAllStories(lang)
            call.respond(response)
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personStoryService.getStoryById(id, lang)
            call.respond(response)
        }

        get("/person/{personId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val personId = call.parameters["personId"]?.toIntOrNull()
            val response = personStoryService.getStoriesByPersonId(personId, lang)
            call.respond(response)
        }

        post {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreatePersonStoryRequest>()
            val response = personStoryService.addStory(request, lang)
            call.respond(response)
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdatePersonStoryRequest>()
            val response = personStoryService.updateStory(id, request, lang)
            call.respond(response)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personStoryService.deleteStory(id, lang)
            call.respond(response)
        }
    }
}
