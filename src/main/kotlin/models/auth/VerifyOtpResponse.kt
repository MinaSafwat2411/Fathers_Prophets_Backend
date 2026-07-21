package com.fathersprophets.backend.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpResponse(
    val verifyToken: String? = null
)