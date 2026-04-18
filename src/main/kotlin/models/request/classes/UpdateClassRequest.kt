package com.fathersprophets.backend.models.request.classes

import kotlinx.serialization.Serializable

@Serializable
data class UpdateClassRequest(
    val id: Int,
    val name: String,
    val image: String?
)
