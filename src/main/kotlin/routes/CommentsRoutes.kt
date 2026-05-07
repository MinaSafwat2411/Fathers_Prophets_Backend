package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.comments.AddCommentRequest
import com.fathersprophets.backend.models.comments.UpdateCommentRequest
import com.fathersprophets.backend.services.comments.ICommentsService
import com.fathersprophets.backend.utils.CommentEventBroadcaster
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.launch

fun Route.commentRoutes(commentsService: ICommentsService) {
    route("/comments") {
        post("/add") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<AddCommentRequest>()
            val response = commentsService.addComment(request, lang)
            call.respond(response)
        }

        put("/update") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<UpdateCommentRequest>()
            val response = commentsService.updateComment(request, lang)
            call.respond(response)
        }

        delete("/delete/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val response = commentsService.deleteComment(id, lang)
            call.respond(response)
        }

        get("/user/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val userId = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid User ID")
            val response = commentsService.getCommentsByUserId(userId, lang)
            call.respond(response)
        }

        webSocket("/user/{id}") {
            try {
                val lang = call.request.headers["Accept-Language"] ?: "en"
                val userId = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid User ID")

                // Send initial comments
                val response = commentsService.getCommentsByUserId(userId, lang)
                sendSerialized(response)

                // Listen for broadcast events for this user
                val job = launch {
                    CommentEventBroadcaster.commentEvents.collect { (broadcastUserId, commentResponse) ->
                        if (broadcastUserId == userId) {
                            sendSerialized(commentResponse)
                        }
                    }
                }

                // Keep connection alive by listening for client frames
                for (frame in incoming) {
                    if (frame is Frame.Close) {
                        job.cancel()
                        close(CloseReason(CloseReason.Codes.NORMAL, "Client disconnected"))
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, e.message ?: "Unknown error"))
            }
        }

        get("/all") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val response = commentsService.getAllComments(lang)
            call.respond(response)
        }
    }
}


