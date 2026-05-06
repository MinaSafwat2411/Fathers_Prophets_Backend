package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.classes.ClassResponse

data class ClassDto(
    val id: Int,
    val name: String,
    val image: String?
){
    fun toClassResponse(): ClassResponse {
        return ClassResponse(
            id = this.id,
            name = this.name,
            image = this.image
        )
    }
}