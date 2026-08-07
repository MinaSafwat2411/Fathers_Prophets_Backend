package com.fathersprophets.backend.modules.chat

import kotlinx.serialization.Serializable

@Serializable
data class AnonymousChatDto(
    val id: Int,
    val sender: Int,
    val receiver: Int,
    val lastMessage: String?,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class AnonymousChatCreateDto(
    val sender: Int,
    val receiver: Int,
    val lastMessage: String? = null
)

@Serializable
data class AnonymousChatUpdateDto(
    val lastMessage: String? = null
)