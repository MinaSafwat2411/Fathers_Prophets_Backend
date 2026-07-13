package com.fathersprophets.backend.routes.activity.escapeegypt

import com.fathersprophets.backend.models.escapeegyptquestion.CreateEscapeEgyptQuestionRequest
import com.fathersprophets.backend.models.escapeegyptquestion.UpdateEscapeEgyptQuestionRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.activity.escapeegypt.escapeegyptquestion.IEscapeEgyptQuestionService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.escapeEgyptQuestionRoutes(service: IEscapeEgyptQuestionService) {
    route("/escape-egypt-questions") {

        get {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllQuestions(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.getQuestionById(id, lang))
        }

        get("/escape-egypt/{escapeEgyptId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val escapeEgyptId = call.parameters["escapeEgyptId"]?.toIntOrNull()
            call.respond(service.getQuestionsByEscapeEgyptId(escapeEgyptId, lang))
        }

        post {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateEscapeEgyptQuestionRequest>()
            call.respond(service.createQuestion(request, lang))
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateEscapeEgyptQuestionRequest>()
            call.respond(service.updateQuestion(id, request, lang))
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteQuestion(id, lang))
        }
    }
}