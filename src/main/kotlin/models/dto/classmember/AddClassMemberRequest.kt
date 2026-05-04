package com.fathersprophets.backend.models.dto.classmember

import kotlinx.serialization.Serializable

@Serializable
data class AddClassMemberRequest(
    val userId: Int? = null,
    val classId: Int? = null,
    val isTeacher: Boolean? = null,
    val name: String? = null,
    val image: String? = null
)