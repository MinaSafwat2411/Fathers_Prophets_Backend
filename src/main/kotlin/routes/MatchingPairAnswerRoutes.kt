package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.matchingpairanswer.CreateMatchingPairAnswerRequest
import com.fathersprophets.backend.models.matchingpairanswer.UpdateMatchingPairAnswerRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.matchingpairanswer.IMatchingPairAnswerService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.matchingPairAnswerRoutes(service: IMatchingPairAnswerService) {
    route("/matching-pair-answers") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllAnswers(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.getAnswerById(id, lang))
        }

        get("/pair/{pairId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val pairId = call.parameters["pairId"]?.toIntOrNull()
            call.respond(service.getAnswersByPairId(pairId, lang))
        }

        get("/user/{userId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val userId = call.parameters["userId"]?.toIntOrNull()
            call.respond(service.getAnswersByUserId(userId, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateMatchingPairAnswerRequest>()
            call.respond(service.createAnswer(request, lang))
        }

        put("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateMatchingPairAnswerRequest>()
            call.respond(service.updateAnswer(id, request, lang))
        }

        delete("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteAnswer(id, lang))
        }
    }
}