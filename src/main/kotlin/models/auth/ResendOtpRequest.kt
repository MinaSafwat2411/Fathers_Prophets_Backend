package com.fathersprophets.backend.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class ResendOtpRequest(
    val transactionId: String? = null
)