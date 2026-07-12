package com.fathersprophets.backend.services.notification

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.notification.CreateNotificationRequest
import com.fathersprophets.backend.models.notification.NotificationResponse
import com.fathersprophets.backend.models.notification.UpdateNotificationRequest

interface INotificationService {
    fun getAllNotifications(lang: String): ApiResponse<List<NotificationResponse>>
    fun getNotificationById(id: Int?, lang: String): ApiResponse<NotificationResponse>
    fun getNotificationsByEventId(eventId: Int?, lang: String): ApiResponse<List<NotificationResponse>>
    fun createNotification(request: CreateNotificationRequest, lang: String): ApiResponse<Int>
    fun updateNotification(id: Int?, request: UpdateNotificationRequest, lang: String): ApiResponse<Nothing>
    fun deleteNotification(id: Int?, lang: String): ApiResponse<Nothing>
}