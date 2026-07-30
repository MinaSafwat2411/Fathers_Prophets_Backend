package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.anonymouschatmessage.AnonymousChatMessageResponse

data class AnonymousChatMessageDto(
    val id: Int,
    val chatId: Int,
    val memberId: Int,
    val servantId: Int,
    val message: String,
    val memberName: String? = null,
    val servantName: String? = null,
    val isRead: Boolean,
    val createdAt: String
) {
    fun convertToResponse() = AnonymousChatMessageResponse(
        id = id,
        chatId = chatId,
        memberId = memberId,
        servantId = servantId,
        message = message,
        memberName = memberName,
        servantName = servantName,
        isRead = isRead,
        createdAt = createdAt
    )
}