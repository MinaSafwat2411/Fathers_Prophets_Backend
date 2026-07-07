package com.fathersprophets.backend.models.notification

import kotlinx.serialization.Serializable

@Serializable
data class UpdateNotificationRequest(
    val isRead: Boolean? = null
)