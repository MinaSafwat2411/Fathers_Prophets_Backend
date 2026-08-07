package com.fathersprophets.backend.modules.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class SendResetOtpResponse(
    val transactionId: String
)