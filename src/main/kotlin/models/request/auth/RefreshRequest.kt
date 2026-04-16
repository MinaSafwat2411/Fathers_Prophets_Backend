package com.fathersprophets.backend.models.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequest(
    val refreshToken : String
)