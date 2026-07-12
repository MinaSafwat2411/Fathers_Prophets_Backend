package com.fathersprophets.backend.database.repository.notification

import com.fathersprophets.backend.database.dao.event.EventDao
import com.fathersprophets.backend.database.dao.notification.NotificationDao
import com.fathersprophets.backend.database.dao.users.UserDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.notification.CreateNotificationRequest
import com.fathersprophets.backend.models.notification.NotificationResponse
import com.fathersprophets.backend.models.notification.UpdateNotificationRequest
import com.fathersprophets.backend.services.notification.IFirebaseMessagingService
import com.fathersprophets.backend.utils.Localization

class NotificationRepository(
    private val notificationDao: NotificationDao,
    private val eventDao: EventDao,
    private val userDao: UserDao,
    private val firebaseMessagingService: IFirebaseMessagingService
) : INotificationRepository {

    override fun getAllNotifications(lang: String): ApiResponse<List<NotificationResponse>> {
        val notifications = notificationDao.findAll()
        return ApiResponse(
            success = true,
            data = notifications.map { it.convertToResponse() },
            message = Localization.get("notifications_retrieved_successfully", lang)
        )
    }

    override fun getNotificationById(id: Int, lang: String): ApiResponse<NotificationResponse> {
        val notification = notificationDao.findById(id)
        return ApiResponse(
            success = true,
            data = notification?.convertToResponse(),
            message = Localization.get("notification_retrieved_successfully", lang)
        )
    }

    override fun getNotificationsByEventId(eventId: Int, lang: String): ApiResponse<List<NotificationResponse>> {
        val notifications = notificationDao.findByEventId(eventId)
        return ApiResponse(
            success = true,
            data = notifications.map { it.convertToResponse() },
            message = Localization.get("notifications_retrieved_successfully", lang)
        )
    }

    override fun createNotification(request: CreateNotificationRequest, lang: String): ApiResponse<Int> {

        val id = notificationDao.create(request.toNotificationDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("notification_creation_failed", lang))

        val tokens = userDao.findAllFcmTokens()

        firebaseMessagingService.sendToTokens(
            tokens,
            request.title?:"",
            request.message ?: "",
            data = mapOf("notificationId" to id.toString())
        )

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("notification_created_successfully", lang)
        )
    }

    override fun updateNotification(id: Int, request: UpdateNotificationRequest, lang: String): ApiResponse<Nothing> {

        val updated = notificationDao.update(request.convertToDto(id))

        if (!updated) throw IllegalArgumentException(Localization.get("notification_update_failed", lang))


        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("notification_updated_successfully", lang)
        )
    }

    override fun deleteNotification(id: Int, lang: String): ApiResponse<Nothing> {
        notificationDao.delete(id)
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("notification_deleted_successfully", lang)
        )
    }
}