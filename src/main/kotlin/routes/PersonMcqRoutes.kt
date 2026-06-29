package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.personmcq.CreatePersonMcqRequest
import com.fathersprophets.backend.models.personmcq.UpdatePersonMcqRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.personmcq.IPersonMcqService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.personMcqRoutes(personMcqService: IPersonMcqService) {
    route("/person-mcq") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = personMcqService.getAllPersonMcqs(lang)
            call.respond(response)
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personMcqService.getPersonMcqById(id, lang)
            call.respond(response)
        }

        post {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreatePersonMcqRequest>()
            val response = personMcqService.createPersonMcq(request, lang)
            call.respond(response)
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdatePersonMcqRequest>()
            val response = personMcqService.updatePersonMcq(id, request, lang)
            call.respond(response)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personMcqService.deletePersonMcq(id, lang)
            call.respond(response)
        }
    }
}