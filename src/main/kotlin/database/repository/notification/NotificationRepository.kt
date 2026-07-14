package com.fathersprophets.backend.database.repository.notification

import com.fathersprophets.backend.database.dao.notification.NotificationDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.notification.NotificationResponse
import com.fathersprophets.backend.utils.Localization

class NotificationRepository(
    private val notificationDao: NotificationDao,
) : INotificationRepository {

    override fun getAllNotifications(lang: String): ApiResponse<List<NotificationResponse>> {
        val notifications = notificationDao.findAll()
        return ApiResponse(
            success = true,
            data = notifications.map { it.convertToResponse() },
            message = Localization.get("notifications_retrieved_successfully", lang)
        )
    }
}