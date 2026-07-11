package com.fathersprophets.backend.database.repository.chat.anonymouschat

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.anonymouschat.AnonymousChatResponse
import com.fathersprophets.backend.models.anonymouschat.CreateAnonymousChatRequest
import com.fathersprophets.backend.models.anonymouschat.UpdateAnonymousChatRequest

interface IAnonymousChatRepository {
    fun getAllAnonymousChats(lang: String): ApiResponse<List<AnonymousChatResponse>>
    fun getAnonymousChatById(id: Int, lang: String): ApiResponse<AnonymousChatResponse>
    fun createAnonymousChat(request: CreateAnonymousChatRequest, lang: String): ApiResponse<Int>
    fun updateAnonymousChat(id: Int, request: UpdateAnonymousChatRequest, lang: String): ApiResponse<Nothing>
    fun deleteAnonymousChat(id: Int, lang: String): ApiResponse<Nothing>
}