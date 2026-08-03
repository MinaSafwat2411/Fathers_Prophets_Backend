package com.fathersprophets.backend.database.dto.chat

data class AnonymousChatDto(
    val id: Int,
    val memberId: Int,
    val servantId: Int,
    val lastMessage: String?,
)