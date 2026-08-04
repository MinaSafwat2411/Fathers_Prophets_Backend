package com.fathersprophets.backend.modules.person

import com.fathersprophets.backend.database.enums.PersonType
import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(
    val id: Int,
    val name: String,
    val nickname: String?,
    val shortStory: String?,
    val fullStory: String?,
    val image: String?,
    val type: PersonType
)

@Serializable
data class PersonCreateDto(
    val name: String,
    val nickname: String? = null,
    val shortStory: String? = null,
    val fullStory: String? = null,
    val image: String? = null,
    val type: PersonType
)

@Serializable
data class PersonUpdateDto(
    val name: String? = null,
    val nickname: String? = null,
    val shortStory: String? = null,
    val fullStory: String? = null,
    val image: String? = null,
    val type: PersonType? = null
)