package com.fathersprophets.backend.database.dto.chat

data class AnonymousChatMessageDto(
    val id: Int,
    val chatId: Int,
    val memberId: Int,
    val servantId: Int,
    val message: String,
    val isRead: Boolean,
)