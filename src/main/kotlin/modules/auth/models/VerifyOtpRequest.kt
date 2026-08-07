package com.fathersprophets.backend.modules.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpRequest(
    val otpCode: String,
    val fcmToken: String
)