package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.classmember.ClassMemberResponse

data class ClassMemberDto(
    val id: Int,
    val name: String,
    val image: String?,
    val isTeacher: Boolean,
    val classId: Int,
    val userId : Int
){
    fun toClassMemberResponse() : ClassMemberResponse {
        return ClassMemberResponse(
            id = this.id,
            name = this.name,
            image = this.image,
            isTeacher = this.isTeacher,
            classId = this.classId,
            userId = this.userId
        )
    }
}