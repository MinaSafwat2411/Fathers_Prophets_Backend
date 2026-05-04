package com.fathersprophets.backend.models.dto.classes

data class ClassMemberResponse(
    val id: Int,
    val name: String,
    val image: String?,
    val isTeacher: Boolean,
    val classId: Int
)
