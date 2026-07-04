package com.fathersprophets.backend.database.repository.anonymouschat

import com.fathersprophets.backend.database.dao.AnonymousChatDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.anonymouschat.AnonymousChatResponse
import com.fathersprophets.backend.models.anonymouschat.CreateAnonymousChatRequest
import com.fathersprophets.backend.models.anonymouschat.UpdateAnonymousChatRequest
import com.fathersprophets.backend.models.dto.AnonymousChatDto
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

    override fun getAnonymousChatById(id: Int, lang: String): ApiResponse<AnonymousChatResponse> {
        val chat = dao.findById(id)
        return ApiResponse(
            success = true,
            data = chat?.convertToResponse(),
            message = Localization.get("anonymous_chat_retrieved_successfully", lang)
        )
    }

    override fun createAnonymousChat(request: CreateAnonymousChatRequest, lang: String): ApiResponse<AnonymousChatResponse> {
        val id = dao.create(request.toAnonymousChatDto())
        val created = dao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("anonymous_chat_created_successfully", lang)
        )
    }

    override fun updateAnonymousChat(id: Int, request: UpdateAnonymousChatRequest, lang: String): ApiResponse<AnonymousChatResponse> {
        dao.update(request.toAnonymousChatDto(id))
        val updated = dao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("anonymous_chat_updated_successfully", lang)
        )
    }

    override fun deleteAnonymousChat(id: Int, lang: String): ApiResponse<Nothing> {
        dao.delete(idToDto(id))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("anonymous_chat_deleted_successfully", lang)
        )
    }

    private fun idToDto(id: Int) = AnonymousChatDto(
        id = id,
        memberId = 0,
        servantId = 0,
        lastMessage = null,
        createdAt = "",
        updatedAt = ""
    )
}