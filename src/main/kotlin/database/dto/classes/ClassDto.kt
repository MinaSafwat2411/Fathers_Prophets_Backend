package com.fathersprophets.backend.database.dto.classes

data class ClassDto(
    val id: Int,
    val familyId: Int,
    val name: String,
    val image: String?
)