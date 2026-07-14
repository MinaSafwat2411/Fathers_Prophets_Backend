package com.fathersprophets.backend.services.notification

import com.fathersprophets.backend.database.repository.notification.INotificationRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.notification.CreateNotificationRequest
import com.fathersprophets.backend.models.notification.NotificationResponse
import com.fathersprophets.backend.models.notification.UpdateNotificationRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class NotificationService(
    private val repository: INotificationRepository
) : INotificationService {

    override fun getAllNotifications(lang: String): ApiResponse<List<NotificationResponse>> {
        return repository.getAllNotifications(lang)
    }
}