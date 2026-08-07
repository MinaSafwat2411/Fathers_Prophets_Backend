package com.fathersprophets.backend.modules.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class ResendResetOtpRequest(
    val transactionId: String
)