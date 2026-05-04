package com.fathersprophets.backend.models.dto.users

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val profile: String? = null
)