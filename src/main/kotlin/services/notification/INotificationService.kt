package com.fathersprophets.backend.services.notification

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.notification.CreateNotificationRequest
import com.fathersprophets.backend.models.notification.NotificationResponse
import com.fathersprophets.backend.models.notification.UpdateNotificationRequest

interface INotificationService {
    fun getAllNotifications(lang: String): ApiResponse<List<NotificationResponse>>
}