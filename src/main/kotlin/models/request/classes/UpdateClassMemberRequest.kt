package com.fathersprophets.backend.models.request.classes

data class UpdateClassMemberRequest(
    val id: Int,
    val userId: Int,
    val classId: Int,
    val isTeacher : Boolean,
)
