package com.fathersprophets.backend.models.anonymouschat

import com.fathersprophets.backend.database.dto.chat.AnonymousChatDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAnonymousChatRequest(
    val lastMessage: String? = null
) {
    fun toAnonymousChatDto(id: Int) = AnonymousChatDto(
        id = id,
        memberId = 0,
        servantId = 0,
        lastMessage = lastMessage,
        createdAt = "",
        updatedAt = ""
    )
}