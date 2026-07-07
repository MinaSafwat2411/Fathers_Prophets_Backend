package com.fathersprophets.backend.services.notification

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification as FcmNotification
import org.slf4j.LoggerFactory

class FirebaseMessagingService : IFirebaseMessagingService {
    private val logger = LoggerFactory.getLogger(FirebaseMessagingService::class.java)

    // FCM caps multicast sends at 500 tokens per request
    private val maxTokensPerBatch = 500

    override fun sendToToken(token: String, title: String, body: String, data: Map<String, String>): Boolean {
        val message = Message.builder()
            .setToken(token)
            .setNotification(FcmNotification.builder().setTitle(title).setBody(body).build())
            .putAllData(data)
            .build()

        return try {
            FirebaseMessaging.getInstance().send(message)
            true
        } catch (e: FirebaseMessagingException) {
            logger.warn("Failed to send FCM push to token: ${e.message}")
            false
        }
    }

    override fun sendToTokens(tokens: List<String>, title: String, body: String, data: Map<String, String>) {
        if (tokens.isEmpty()) return

        tokens.chunked(maxTokensPerBatch).forEach { batch ->
            val message = MulticastMessage.builder()
                .addAllTokens(batch)
                .setNotification(FcmNotification.builder().setTitle(title).setBody(body).build())
                .putAllData(data)
                .build()

            try {
                val response = FirebaseMessaging.getInstance().sendEachForMulticast(message)
                if (response.failureCount > 0) {
                    logger.warn("FCM multicast: ${response.failureCount} of ${batch.size} messages failed")
                }
            } catch (e: FirebaseMessagingException) {
                logger.warn("Failed to send FCM multicast push: ${e.message}")
            }
        }
    }
}