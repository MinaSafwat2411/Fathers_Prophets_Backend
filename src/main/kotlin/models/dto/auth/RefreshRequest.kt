package com.fathersprophets.backend.models.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequest(
    val refreshToken : String? = null
)