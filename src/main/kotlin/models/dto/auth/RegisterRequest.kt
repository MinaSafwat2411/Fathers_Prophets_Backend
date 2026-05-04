package com.fathersprophets.backend.models.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String? = null,
    val username: String? = null,
    val password: String? = null,
)