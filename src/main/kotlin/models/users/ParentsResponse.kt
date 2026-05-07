package com.fathersprophets.backend.models.users

import kotlinx.serialization.Serializable

@Serializable
data class ParentsResponse(
    val motherPhone : String? = null,
    val fatherPhone : String? = null,
)
