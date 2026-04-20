package com.fathersprophets.backend.models.request.classmember

import kotlinx.serialization.Serializable

@Serializable
data class UpdateClassMemberRequest(
    val userId: Int,
    val classId: Int,
    val isTeacher : Boolean,
    val name: String,
    val image: String?
)