package com.fathersprophets.backend.models.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val username: String,
    val password: String,
)