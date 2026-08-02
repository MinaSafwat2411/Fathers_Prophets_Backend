package com.fathersprophets.backend.database.dto

data class UserNotificationDto(
    val id: Int,
    val notificationId: Int,
    val userId: Int,
    val isRead: Boolean,
)
