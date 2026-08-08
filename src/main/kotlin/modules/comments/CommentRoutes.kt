package com.fathersprophets.backend.modules.comments

import com.fathersprophets.backend.database.tables.comments.CommentCreateDto
import com.fathersprophets.backend.database.tables.comments.CommentUpdateDto
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.modules.comments.service.ICommentService
import com.fathersprophets.backend.utils.Localization
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.commentRoutes(commentService: ICommentService) {
    route("/comments") {
        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(commentService.getAll(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(commentService.getById(id, lang))
        }

        get("/user/{userId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val userId = call.parameters["userId"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(commentService.getByUserId(userId, lang))
        }

        get("/teacher/{teacherId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val teacherId = call.parameters["teacherId"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(commentService.getByTeacherId(teacherId, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CommentCreateDto>()
            call.respond(commentService.create(request, lang))
        }

        put("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            val request = call.receive<CommentUpdateDto>()
            call.respond(commentService.update(id, request, lang))
        }

        delete("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(commentService.delete(id, lang))
        }
    }
}