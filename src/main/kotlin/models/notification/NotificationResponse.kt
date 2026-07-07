package com.fathersprophets.backend.models.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
    val id: Int,
    val eventId: Int,
    val type: String,
    val title: String,
    val message: String?,
    val isRead: Boolean,
    val createdAt: String
)