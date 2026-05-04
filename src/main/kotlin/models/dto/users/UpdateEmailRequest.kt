package com.fathersprophets.backend.models.dto.users

import kotlinx.serialization.Serializable

@Serializable
data class UpdateEmailRequest(
    val email: String? = null
)
