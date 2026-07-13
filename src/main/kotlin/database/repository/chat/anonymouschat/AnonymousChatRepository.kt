package com.fathersprophets.backend.database.repository.chat.anonymouschat

import com.fathersprophets.backend.database.dao.chat.AnonymousChatDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.anonymouschat.AnonymousChatResponse
import com.fathersprophets.backend.models.anonymouschat.CreateAnonymousChatRequest
import com.fathersprophets.backend.models.anonymouschat.UpdateAnonymousChatRequest
import com.fathersprophets.backend.utils.Localization

class AnonymousChatRepository(
    private val dao: AnonymousChatDao
) : IAnonymousChatRepository {

    override fun getAllAnonymousChats(lang: String): ApiResponse<List<AnonymousChatResponse>> {
        val chats = dao.findAll()
        return ApiResponse(
            success = true,
            data = chats.map { it.convertToResponse() },
            message = Localization.get("anonymous_chats_retrieved_successfully", lang)
        )
    }

    override fun createAnonymousChat(request: CreateAnonymousChatRequest, lang: String): ApiResponse<AnonymousChatResponse> {

        val created = dao.create(request.toAnonymousChatDto())
            ?: throw IllegalStateException(Localization.get("anonymous_chat_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = created.convertToResponse(),
            message = Localization.get("anonymous_chat_created_successfully", lang)
        )
    }

    override fun updateAnonymousChat(id: Int, request: UpdateAnonymousChatRequest, lang: String): ApiResponse<AnonymousChatResponse> {

        val  updated = dao.update(request.toAnonymousChatDto(id))
            ?: throw IllegalStateException(Localization.get("anonymous_chat_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updated.convertToResponse(),
            message = Localization.get("anonymous_chat_updated_successfully", lang)
        )
    }

    override fun deleteAnonymousChat(id: Int, lang: String): ApiResponse<AnonymousChatResponse> {
        val  deleted = dao.delete(id)

        if (!deleted) throw IllegalStateException(Localization.get("anonymous_chat_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("anonymous_chat_deleted_successfully", lang)
        )
    }

    override fun getMemberChat(memberId: Int, lang: String) : ApiResponse<List<AnonymousChatResponse>> {
        val chat = dao.findChatByMemberId(memberId)

        return ApiResponse(
            success = true,
            data = chat.map { it.convertToResponse() },
            message = Localization.get("anonymous_chat_retrieved_successfully", lang)
        )
    }

    override fun getServantChat(servantId: Int, lang: String) : ApiResponse<List<AnonymousChatResponse>> {
        val chat = dao.findChatByServantId(servantId)

        return ApiResponse(
            success = true,
            data = chat.map { it.convertToResponse() },
            message = Localization.get("anonymous_chat_retrieved_successfully", lang)
        )
    }
}