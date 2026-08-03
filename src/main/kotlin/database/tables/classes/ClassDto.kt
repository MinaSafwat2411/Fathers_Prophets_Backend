package com.fathersprophets.backend.database.tables.classes

import kotlinx.serialization.Serializable

@Serializable
data class ClassDto(
    val id: Int,
    val name: String,
    val image: String?,
    val familyId: Int
)

@Serializable
data class ClassCreateDto(
    val name: String,
    val image: String? = null,
    val familyId: Int
)

@Serializable
data class ClassUpdateDto(
    val name: String? = null,
    val image: String? = null,
    val familyId: Int? = null
)