package com.fathersprophets.backend.models.classmember

import com.fathersprophets.backend.models.dto.ClassMemberDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateClassMemberRequest(
    val userId: Int,
    val classId: Int,
    val isTeacher: Boolean,
    val name: String,
    val image: String?
) {
    fun toClassMemberDto(id: Int) = ClassMemberDto(
        id = id,
        name = name,
        image = image,
        isTeacher = isTeacher,
        classId = classId,
        userId = userId
    )
}