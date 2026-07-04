package com.fathersprophets.backend.models.anonymouschatmessage

import kotlinx.serialization.Serializable

@Serializable
data class AnonymousChatMessageResponse(
    val id: Int,
    val chatId: Int,
    val memberId: Int,
    val servantId: Int,
    val message: String,
    val isRead: Boolean,
    val createdAt: String
)