package com.fathersprophets.backend.models.classmember

import kotlinx.serialization.Serializable

@Serializable
data class ClassMemberResponse(
    val id: Int,
    val name: String,
    val image: String?,
    val isTeacher: Boolean,
    val classId: Int,
    val userId : Int
)