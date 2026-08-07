package com.fathersprophets.backend.modules.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class VerifyResetOtpRequest(
    val transactionId: String,
    val otpCode: String
)