package com.fathersprophets.backend.models.dto.version

import kotlinx.serialization.Serializable

@Serializable
data class AdminPinRequest(
    val version: String,
    val adminPin: String
)
