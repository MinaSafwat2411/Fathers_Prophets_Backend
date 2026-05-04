package com.fathersprophets.backend.models.dto.version

import kotlinx.serialization.Serializable

@Serializable
data class AdminPin(
    val id : Int,
    val version: String,
    val adminPin: String
)
