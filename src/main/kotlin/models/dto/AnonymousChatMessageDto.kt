package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.anonymouschatmessage.AnonymousChatMessageResponse

data class AnonymousChatMessageDto(
    val id: Int,
    val chatId: Int,
    val memberId: Int,
    val servantId: Int,
    val message: String,
    val isRead: Boolean,
    val createdAt: String
) {
    fun convertToResponse() = AnonymousChatMessageResponse(
        id = id,
        chatId = chatId,
        memberId = memberId,
        servantId = servantId,
        message = message,
        isRead = isRead,
        createdAt = createdAt
    )
}