package com.fathersprophets.backend.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class SendOtpRequest(
    val username: String? = null,
    val email: String? = null
)