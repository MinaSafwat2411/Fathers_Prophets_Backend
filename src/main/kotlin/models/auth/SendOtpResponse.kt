package com.fathersprophets.backend.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class SendOtpResponse(
    val transactionId: String? = null
)