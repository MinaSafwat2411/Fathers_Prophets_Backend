package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.anonymouschat.AnonymousChatResponse

data class AnonymousChatDto(
    val id: Int,
    val memberId: Int,
    val servantId: Int,
    val lastMessage: String?,
    val createdAt: String,
    val updatedAt: String
) {
    fun convertToResponse() = AnonymousChatResponse(
        id = id,
        memberId = memberId,
        servantId = servantId,
        lastMessage = lastMessage,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}