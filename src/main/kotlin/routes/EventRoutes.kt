package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.event.EventRequest
import com.fathersprophets.backend.services.events.IEventService
import com.fathersprophets.backend.utils.EventBroadcaster
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun Route.eventRoutes(
    eventService: IEventService,
) {
    route("/events") {
        webSocket {
            try {
                val lang = call.request.header("Accept-Language") ?: "en"
                val initialResponse = eventService.getAllEvents(lang)
                sendSerialized(initialResponse)

                val job = launch {
                    EventBroadcaster.eventFlow.collectLatest { events ->
                        sendSerialized(events)
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
            val id = call.parameters["id"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            call.respond(eventService.getEventById(id, lang))
        }
        post {
            val lang = call.request.header("Accept-Language") ?: "en"
            val request = call.receive<EventRequest>()
            val response = eventService.addEvent(request, lang)
            EventBroadcaster.broadcastEvents(eventService.getAllEvents(lang))
            call.respond(response)
        }
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val request = call.receive<EventRequest>()
            val response = eventService.updateEvent(id, request, lang)
            EventBroadcaster.broadcastEvents(eventService.getAllEvents(lang))
            call.respond(response)
        }
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val response = eventService.deleteEvent(id, lang)
            EventBroadcaster.broadcastEvents(eventService.getAllEvents(lang))
            call.respond(response)
        }
        get("/count") {
            val lang = call.request.header("Accept-Language") ?: "en"
            call.respond(eventService.getEventsCount(lang))
        }
    }
}
