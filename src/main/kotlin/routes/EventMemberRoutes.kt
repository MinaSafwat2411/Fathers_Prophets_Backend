package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.eventmember.EventMemberRequest
import com.fathersprophets.backend.services.eventmember.IEventMemberService
import com.fathersprophets.backend.utils.EventMemberBroadcaster
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun Route.eventMemberRoutes(
    eventMemberService: IEventMemberService
) {
    route("/event-members") {
        post {
            val lang = call.request.header("Accept-Language") ?: "en"
            val request = call.receive<EventMemberRequest>()
            val response = eventMemberService.addEventMember(request, lang)

            request.eventId.let { eventId ->
                EventMemberBroadcaster.broadcastEventMembers(
                    eventId,
                    eventMemberService.getEventMembersByEventId(eventId, lang)
                )
            }

            call.respond(response)
        }
        delete("/{eventId}") {
            val eventId = call.parameters["eventId"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val response = eventMemberService.deleteEventMember(eventId, lang)

            eventId?.let { id ->
                EventMemberBroadcaster.broadcastEventMembers(
                    id,
                    eventMemberService.getEventMembersByEventId(id, lang)
                )
            }

            call.respond(response)
        }
        webSocket("/event/{eventId}") {
            try {
                val eventId = call.parameters["eventId"]?.toIntOrNull()
                val lang = call.request.header("Accept-Language") ?: "en"

                if (eventId == null) {
                    close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, "Event ID is required"))
                    return@webSocket
                }

                val initialResponse = eventMemberService.getEventMembersByEventId(eventId, lang)
                sendSerialized(initialResponse)

                val job = launch {
                    EventMemberBroadcaster.eventMemberFlow.collectLatest { (broadcastEventId, membersResponse) ->
                        if (broadcastEventId == eventId) {
                            sendSerialized(membersResponse)
                        }
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
        get("/user/{userId}") {
            val userId = call.parameters["userId"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            call.respond(eventMemberService.getEventMembersByUserId(userId, lang))
        }
    }
}
