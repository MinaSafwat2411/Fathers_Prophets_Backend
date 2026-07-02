package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.quiz.CreateQuizRequest
import com.fathersprophets.backend.models.quiz.UpdateQuizRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.quiz.IQuizService
import com.fathersprophets.backend.utils.QuizBroadcaster
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun Route.quizRoutes(service: IQuizService) {
    route("/quiz") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllQuizzes(lang))
        }

        webSocket {
            try {
                val lang = call.request.headers["Accept-Language"] ?: "en"
                val initialResponse = service.getAllQuizzes(lang)
                sendSerialized(initialResponse)

                val job = launch {
                    QuizBroadcaster.quizFlow.collectLatest { quizzes ->
                        sendSerialized(quizzes)
                    }
                }

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

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.getQuizById(id, lang))
        }

        post {
            call.requireRole("admin", "superadmin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateQuizRequest>()
            val response = service.createQuiz(request, lang)
            QuizBroadcaster.broadcastQuizzes(service.getAllQuizzes(lang))
            call.respond(response)
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateQuizRequest>()
            val response = service.updateQuiz(id, request, lang)
            QuizBroadcaster.broadcastQuizzes(service.getAllQuizzes(lang))
            call.respond(response)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val response = service.deleteQuiz(id, lang)
            QuizBroadcaster.broadcastQuizzes(service.getAllQuizzes(lang))
            call.respond(response)
        }
    }
}