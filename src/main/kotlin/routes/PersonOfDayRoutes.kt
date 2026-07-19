package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.personofday.CreatePersonOfDayRequest
import com.fathersprophets.backend.models.personofday.UpdatePersonOfDayRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.person.personofday.IPersonOfDayService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.personOfDayRoutes(personOfDayService: IPersonOfDayService) {
    route("/person-of-day") {
        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = personOfDayService.getAllPersonsOfDay(lang)
            call.respond(response)
        }

        get("/date/{date}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = personOfDayService.getPersonOfDayByDate(lang)
            call.respond(response)
        }

        post {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreatePersonOfDayRequest>()
            val response = personOfDayService.addPersonOfDay(request, lang)
            call.respond(response)
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdatePersonOfDayRequest>()
            val response = personOfDayService.updatePersonOfDay(id, request, lang)
            call.respond(response)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = personOfDayService.deletePersonOfDay(id, lang)
            call.respond(response)
        }
    }
}