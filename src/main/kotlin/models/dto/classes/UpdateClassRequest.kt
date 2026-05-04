package com.fathersprophets.backend.models.dto.classes

import kotlinx.serialization.Serializable

@Serializable
data class UpdateClassRequest(
    val id: Int? = null,
    val name: String? = null,
    val image: String? = null
)
