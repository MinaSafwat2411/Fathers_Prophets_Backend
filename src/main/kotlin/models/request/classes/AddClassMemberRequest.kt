package com.fathersprophets.backend.models.request.classes

import kotlinx.serialization.Serializable

@Serializable
data class AddClassMemberRequest(
    val userId: Int,
    val classId: Int,
    val isTeacher : Boolean,
    val name: String,
    val image: String?
)