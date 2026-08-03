package com.fathersprophets.backend.database.tables.family

import kotlinx.serialization.Serializable

@Serializable
data class FamilyDto(
    val id: Int,
    val familyName: String,
    val image: String?,
    val leaderId: Int,
    val subLeaderId: Int
)

@Serializable
data class FamilyCreateDto(
    val familyName: String,
    val image: String? = null,
    val leaderId: Int,
    val subLeaderId: Int
)

@Serializable
data class FamilyUpdateDto(
    val familyName: String? = null,
    val image: String? = null,
    val leaderId: Int? = null,
    val subLeaderId: Int? = null
)