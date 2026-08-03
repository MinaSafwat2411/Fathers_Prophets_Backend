package com.fathersprophets.backend.models.anonymouschat

import com.fathersprophets.backend.database.dto.chat.AnonymousChatDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateAnonymousChatRequest(
    val memberId: Int? = null,
    val servantId: Int? = null,
    val lastMessage: String? = null
) {
    fun toAnonymousChatDto() = AnonymousChatDto(
        id = 0,
        memberId = memberId ?: 0,
        servantId = servantId ?: 0,
        lastMessage = lastMessage,
        createdAt = "",
        updatedAt = ""
    )
}