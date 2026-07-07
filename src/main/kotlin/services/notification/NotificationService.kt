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

    override fun getNotificationById(id: Int?, lang: String): ApiResponse<NotificationResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("notification_id_required", lang))
        return repository.getNotificationById(id, lang)
    }

    override fun getNotificationsByEventId(eventId: Int?, lang: String): ApiResponse<List<NotificationResponse>> {
        if (eventId == null) throw IllegalArgumentException(Localization.get("event_id_required", lang))
        return repository.getNotificationsByEventId(eventId, lang)
    }

    override fun createNotification(request: CreateNotificationRequest, lang: String): ApiResponse<NotificationResponse> {
        validateRequired(
            request.eventId to "eventId",
            request.type to "type",
            request.title to "title",
            lang = lang
        )
        return repository.createNotification(request, lang)
    }

    override fun updateNotification(id: Int?, request: UpdateNotificationRequest, lang: String): ApiResponse<NotificationResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("notification_id_required", lang))
        return repository.updateNotification(id, request, lang)
    }

    override fun deleteNotification(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("notification_id_required", lang))
        return repository.deleteNotification(id, lang)
    }
}