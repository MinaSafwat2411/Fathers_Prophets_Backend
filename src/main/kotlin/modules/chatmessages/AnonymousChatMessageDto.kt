package com.fathersprophets.backend.modules.chatmessages

import kotlinx.serialization.Serializable

@Serializable
data class AnonymousChatMessageDto(
    val id: Int,
    val chatId: Int,
    val message: String,
    val isRead: Boolean,
    val createdAt: String
)

@Serializable
data class AnonymousChatMessageCreateDto(
    val chatId: Int,
    val message: String
)

@Serializable
data class AnonymousChatMessageUpdateDto(
    val message: String? = null,
    val isRead: Boolean? = null
)