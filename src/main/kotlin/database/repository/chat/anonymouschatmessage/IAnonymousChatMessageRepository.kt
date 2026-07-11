package com.fathersprophets.backend.database.repository.chat.anonymouschatmessage

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.anonymouschatmessage.AnonymousChatMessageResponse
import com.fathersprophets.backend.models.anonymouschatmessage.CreateAnonymousChatMessageRequest
import com.fathersprophets.backend.models.anonymouschatmessage.UpdateAnonymousChatMessageRequest

interface IAnonymousChatMessageRepository {
    fun getAllMessages(lang: String): ApiResponse<List<AnonymousChatMessageResponse>>
    fun getMessageById(id: Int, lang: String): ApiResponse<AnonymousChatMessageResponse>
    fun getMessagesByChatId(chatId: Int, lang: String): ApiResponse<List<AnonymousChatMessageResponse>>
    fun createMessage(request: CreateAnonymousChatMessageRequest, lang: String): ApiResponse<AnonymousChatMessageResponse>
    fun updateMessage(id: Int, request: UpdateAnonymousChatMessageRequest, lang: String): ApiResponse<AnonymousChatMessageResponse>
    fun deleteMessage(id: Int, lang: String): ApiResponse<Nothing>
}