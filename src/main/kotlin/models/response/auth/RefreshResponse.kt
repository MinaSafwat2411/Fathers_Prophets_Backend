package com.fathersprophets.backend.models.response.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshResponse(
    val token: String,
    val refreshToken: String,
)
