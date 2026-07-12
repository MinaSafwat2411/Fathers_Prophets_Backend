package com.fathersprophets.backend.services.chat.anonymouschat

import com.fathersprophets.backend.database.repository.chat.anonymouschat.IAnonymousChatRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.anonymouschat.AnonymousChatResponse
import com.fathersprophets.backend.models.anonymouschat.CreateAnonymousChatRequest
import com.fathersprophets.backend.models.anonymouschat.UpdateAnonymousChatRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class AnonymousChatService(
    private val repository: IAnonymousChatRepository
) : IAnonymousChatService {

    override fun getAllAnonymousChats(lang: String): ApiResponse<List<AnonymousChatResponse>> {
        return repository.getAllAnonymousChats(lang)
    }

    override fun getAnonymousChatById(id: Int?, lang: String): ApiResponse<AnonymousChatResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("anonymous_chat_id_required", lang))
        return repository.getAnonymousChatById(id, lang)
    }

    override fun createAnonymousChat(request: CreateAnonymousChatRequest, lang: String): ApiResponse<Int> {
        validateRequired(
            request.memberId to "memberId",
            request.servantId to "servantId",
            lang = lang
        )
        return repository.createAnonymousChat(request, lang)
    }

    override fun updateAnonymousChat(
        id: Int?,
        request: UpdateAnonymousChatRequest,
        lang: String
    ): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("anonymous_chat_id_required", lang))
        return repository.updateAnonymousChat(id, request, lang)
    }

    override fun deleteAnonymousChat(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("anonymous_chat_id_required", lang))
        return repository.deleteAnonymousChat(id, lang)
    }

    override fun getMemberChat(
        memberId: Int,
        lang: String
    ): ApiResponse<List<AnonymousChatResponse>> {
        return repository.getMemberChat(memberId, lang)
    }

    override fun getServantChat(
        servantId: Int,
        lang: String
    ): ApiResponse<List<AnonymousChatResponse>> {
        return repository.getServantChat(servantId, lang)
    }
}