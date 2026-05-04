package com.fathersprophets.backend.models.dto.version

import kotlinx.serialization.Serializable

@Serializable
data class VersionRequest(
    val version : String? = null,
    val adminPin : String? = null
)
