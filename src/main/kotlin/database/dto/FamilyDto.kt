package com.fathersprophets.backend.database.dto

data class FamilyDto(
    val id: Int,
    val familyName: String,
    val image: String?,
    val leaderId: Int,
    val subLeaderId: Int
)
