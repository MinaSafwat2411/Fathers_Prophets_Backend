package com.fathersprophets.backend.services.chat.anonymouschat

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.anonymouschat.AnonymousChatResponse
import com.fathersprophets.backend.models.anonymouschat.CreateAnonymousChatRequest
import com.fathersprophets.backend.models.anonymouschat.UpdateAnonymousChatRequest

interface IAnonymousChatService {
    fun getAllAnonymousChats(lang: String): ApiResponse<List<AnonymousChatResponse>>
    fun createAnonymousChat(request: CreateAnonymousChatRequest, lang: String): ApiResponse<AnonymousChatResponse>
    fun updateAnonymousChat(id: Int?, request: UpdateAnonymousChatRequest, lang: String): ApiResponse<AnonymousChatResponse>
    fun deleteAnonymousChat(id: Int?, lang: String): ApiResponse<AnonymousChatResponse>

    fun getMemberChat(memberId: Int?, lang: String) : ApiResponse<List<AnonymousChatResponse>>

    fun getServantChat(servantId: Int?, lang: String) : ApiResponse<List<AnonymousChatResponse>>

}