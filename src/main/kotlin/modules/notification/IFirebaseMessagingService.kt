package com.fathersprophets.backend.modules.notification

interface IFirebaseMessagingService {
    fun sendToToken(token: String, title: String, body: String, data: Map<String, String> = emptyMap()): Boolean
    fun sendToTokens(tokens: List<String>, title: String, body: String, data: Map<String, String> = emptyMap())
}