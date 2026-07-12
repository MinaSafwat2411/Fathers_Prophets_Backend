package com.fathersprophets.backend.database.repository.events.eventmember

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.eventmember.EventMemberRequest
import com.fathersprophets.backend.models.eventmember.EventMemberResponse

interface IEventMemberRepository {

    fun addEventMember(eventMember : EventMemberRequest,lang: String) : ApiResponse<Int>
    fun deleteEventMember(eventId : Int , lang: String) : ApiResponse<Nothing>
    fun getEventMembersByEventId(eventId : Int, lang: String) : ApiResponse<List<EventMemberResponse>>
    fun getEventMembersByUserId(userId : Int, lang: String) : ApiResponse<List<EventMemberResponse>>
}