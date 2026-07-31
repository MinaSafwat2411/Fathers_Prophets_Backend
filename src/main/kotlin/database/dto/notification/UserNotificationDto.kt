package com.fathersprophets.backend.database.dto.notification

data class UserNotificationDto(
    val id: Int,
    val notificationId: Int,
    val userId: Int,
    val isRead: Boolean,
)
