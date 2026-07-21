package com.fathersprophets.backend.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpRequest(
    val transactionId: String? = null,
    val otp: String? = null
)