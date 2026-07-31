package com.fathersprophets.backend.database.dto.notification

import com.fathersprophets.backend.database.enums.NotificationType

data class NotificationDto(
    val id: Int,
    val type: NotificationType,
    val referenceId : Int?,
    val title: String,
    val description: String,
)