package com.fathersprophets.backend.models.person

import kotlinx.serialization.Serializable

@Serializable
data class PersonResponse(
    val id: Int,
    val name: String,
    val nickname: String?,
    val shortStory: String?,
    val fullStory: String?,
    val image: String?,
    val type: String
)