package com.fathersprophets.backend.models.anonymouschatmessage

import com.fathersprophets.backend.database.dto.chat.AnonymousChatMessageDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAnonymousChatMessageRequest(
    val isRead: Boolean? = null
) {
    fun toAnonymousChatMessageDto(id: Int) = AnonymousChatMessageDto(
        id = id,
        chatId = 0,
        memberId = 0,
        servantId = 0,
        message = "",
        isRead = isRead ?: false,
        createdAt = ""
    )
}