package com.fathersprophets.backend.models.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String? = null,
    val password: String ?= null,
    val fcmToken : String ?= null,
)