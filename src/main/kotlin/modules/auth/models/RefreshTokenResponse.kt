package com.fathersprophets.backend.modules.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val token: String,
    val refreshToken: String,
    val expiresAt: Long
)
