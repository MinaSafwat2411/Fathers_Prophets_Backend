package com.fathersprophets.backend.models.classmember

import com.fathersprophets.backend.models.dto.ClassMemberDto
import kotlinx.serialization.Serializable

@Serializable
data class AddClassMemberRequest(
    val userId: Int? = null,
    val classId: Int? = null,
    val isTeacher: Boolean? = null,
    val name: String? = null,
    val image: String? = null
) {
    fun toClassMemberDto() = ClassMemberDto(
        id = 0,
        userId = this.userId ?: 0,
        classId = this.classId ?: 0,
        isTeacher = this.isTeacher ?: false,
        name = this.name ?: "",
        image = this.image ?: ""
    )
}