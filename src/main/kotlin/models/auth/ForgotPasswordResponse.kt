package com.fathersprophets.backend.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordResponse(
    val maskedEmail: String? = null
)