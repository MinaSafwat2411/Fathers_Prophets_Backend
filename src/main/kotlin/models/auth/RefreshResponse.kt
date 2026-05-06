package com.fathersprophets.backend.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshResponse(
    val token: String,
    val refreshToken: String,
)
