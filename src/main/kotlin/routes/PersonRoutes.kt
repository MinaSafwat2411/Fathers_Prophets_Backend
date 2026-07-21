package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.person.CreatePersonRequest
import com.fathersprophets.backend.models.person.UpdatePersonRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.person.IPersonService
import com.fathersprophets.backend.utils.receiveMultipartForm
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

        get("/type/{type}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val personType = call.parameters["type"]
            val response = personService.getPersonByType(personType, lang)
            call.respond(response)
        }

        post {
            call.requireRole("admin", "superadmin","games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val form = call.receiveMultipartForm(lang)
            val createPersonRequest = CreatePersonRequest(
                name = form.fields["name"],
                nickname = form.fields["nickname"],
                shortStory = form.fields["shortStory"],
                fullStory = form.fields["fullStory"],
                image = form.base64Image,
                type = form.fields["type"]
            )
            val response = personService.addPerson(createPersonRequest, lang)
            call.respond(response)
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin","games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val personId = call.parameters["id"]?.toIntOrNull()
            val form = call.receiveMultipartForm(lang)
            val updatePersonRequest = UpdatePersonRequest(
                name = form.fields["name"],
                nickname = form.fields["nickname"],
                shortStory = form.fields["shortStory"],
                fullStory = form.fields["fullStory"],
                image = form.base64Image,
                type = form.fields["type"]
            )
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
