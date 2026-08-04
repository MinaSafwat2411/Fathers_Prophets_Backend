package com.fathersprophets.backend.modules.personofday


import kotlinx.serialization.Serializable

@Serializable
data class PersonOfDayDto(
    val id: Int,
    val personId: Int,
    val message: String,
    val verse: String,
    val date: String
)

@Serializable
data class PersonOfDayCreateDto(
    val personId: Int,
    val message: String,
    val verse: String,
    val date: String
)

@Serializable
data class PersonOfDayUpdateDto(
    val personId: Int? = null,
    val message: String? = null,
    val verse: String? = null,
    val date: String? = null
)