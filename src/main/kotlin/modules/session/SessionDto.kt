package com.fathersprophets.backend.modules.session

import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
    val id: Int,
    val dateTime: String,
    val createdAt: String,
    val familyId: Int
)

@Serializable
data class SessionCreateDto(
    val dateTime: String,
    val familyId: Int
)

@Serializable
data class SessionUpdateDto(
    val dateTime: String? = null,
    val familyId: Int? = null
)