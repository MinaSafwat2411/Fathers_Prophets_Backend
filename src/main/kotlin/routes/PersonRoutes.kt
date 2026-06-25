package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.person.UpdatePersonRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.person.IPersonService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.personRoutes(personService: IPersonService) {
    route("/person") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = personService.getAllPersons(lang)
            call.respond(response)
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val personId = call.parameters["id"]?.toIntOrNull()
            val response = personService.getPersonById(personId, lang)
            call.respond(response)
        }

        post {
            call.requireRole("admin", "superadmin","games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val updatePersonRequest = call.receive<UpdatePersonRequest>()
            val response = personService.addPerson(updatePersonRequest, lang)
            call.respond(response)
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin","games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val personId = call.parameters["id"]?.toIntOrNull()
            val updatePersonRequest = call.receive<UpdatePersonRequest>()
            val response = personService.updatePerson(personId, updatePersonRequest, lang)
            call.respond(response)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin","games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val personId = call.parameters["id"]?.toIntOrNull()
            val response = personService.deletePerson(personId, lang)
            call.respond(response)
        }
    }
}
