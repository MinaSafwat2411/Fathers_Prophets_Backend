package com.fathersprophets.backend.modules.notificationuser

import kotlinx.serialization.Serializable

@Serializable
data class NotificationUserDto(
    val id: Int,
    val notificationId: Int,
    val userId: Int,
    val isRead: Boolean,
    val readAt: String?
)

@Serializable
data class NotificationUserCreateDto(
    val notificationId: Int,
    val userId: Int,
    val isRead: Boolean = false,
    val readAt: String? = null
)

@Serializable
data class NotificationUserUpdateDto(
    val notificationId: Int? = null,
    val userId: Int? = null,
    val isRead: Boolean? = null,
    val readAt: String? = null
)