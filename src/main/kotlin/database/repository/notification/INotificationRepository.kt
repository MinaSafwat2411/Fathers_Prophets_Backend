package com.fathersprophets.backend.database.repository.notification

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.notification.NotificationResponse

interface INotificationRepository {
    fun getAllNotifications(lang: String): ApiResponse<List<NotificationResponse>>
}