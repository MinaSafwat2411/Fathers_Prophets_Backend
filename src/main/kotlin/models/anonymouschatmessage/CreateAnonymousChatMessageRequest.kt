package com.fathersprophets.backend.models.anonymouschatmessage

import com.fathersprophets.backend.models.dto.AnonymousChatMessageDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateAnonymousChatMessageRequest(
    val chatId: Int? = null,
    val memberId: Int? = null,
    val servantId: Int? = null,
    val message: String? = null
) {
    fun toAnonymousChatMessageDto() = AnonymousChatMessageDto(
        id = 0,
        chatId = chatId ?: 0,
        memberId = memberId ?: 0,
        servantId = servantId ?: 0,
        message = message ?: "",
        isRead = false,
        createdAt = ""
    )
}