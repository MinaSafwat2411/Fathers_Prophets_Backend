package com.fathersprophets.backend.services.anonymouschatmessage

import com.fathersprophets.backend.database.repository.anonymouschatmessage.IAnonymousChatMessageRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.anonymouschatmessage.AnonymousChatMessageResponse
import com.fathersprophets.backend.models.anonymouschatmessage.CreateAnonymousChatMessageRequest
import com.fathersprophets.backend.models.anonymouschatmessage.UpdateAnonymousChatMessageRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class AnonymousChatMessageService(
    private val repository: IAnonymousChatMessageRepository
) : IAnonymousChatMessageService {

    override fun getAllMessages(lang: String): ApiResponse<List<AnonymousChatMessageResponse>> {
        return repository.getAllMessages(lang)
    }

    override fun getMessageById(id: Int?, lang: String): ApiResponse<AnonymousChatMessageResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("anonymous_chat_message_id_required", lang))
        return repository.getMessageById(id, lang)
    }

    override fun getMessagesByChatId(chatId: Int?, lang: String): ApiResponse<List<AnonymousChatMessageResponse>> {
        if (chatId == null) throw IllegalArgumentException(Localization.get("anonymous_chat_id_required", lang))
        return repository.getMessagesByChatId(chatId, lang)
    }

    override fun createMessage(request: CreateAnonymousChatMessageRequest, lang: String): ApiResponse<AnonymousChatMessageResponse> {
        validateRequired(
            request.chatId to "chatId",
            request.memberId to "memberId",
            request.servantId to "servantId",
            request.message to "message",
            lang = lang
        )
        return repository.createMessage(request, lang)
    }

    override fun updateMessage(id: Int?, request: UpdateAnonymousChatMessageRequest, lang: String): ApiResponse<AnonymousChatMessageResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("anonymous_chat_message_id_required", lang))
        return repository.updateMessage(id, request, lang)
    }

    override fun deleteMessage(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("anonymous_chat_message_id_required", lang))
        return repository.deleteMessage(id, lang)
    }
}