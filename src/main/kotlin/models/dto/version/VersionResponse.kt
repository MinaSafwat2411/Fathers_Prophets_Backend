package com.fathersprophets.backend.models.dto.version

import kotlinx.serialization.Serializable

@Serializable
data class VersionResponse(
    val id: Int,
    val version: String,
)