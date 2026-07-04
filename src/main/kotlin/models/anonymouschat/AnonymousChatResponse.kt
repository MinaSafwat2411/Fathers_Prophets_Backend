package com.fathersprophets.backend.models.anonymouschat

import kotlinx.serialization.Serializable

@Serializable
data class AnonymousChatResponse(
    val id: Int,
    val memberId: Int,
    val servantId: Int,
    val lastMessage: String?,
    val createdAt: String,
    val updatedAt: String
)