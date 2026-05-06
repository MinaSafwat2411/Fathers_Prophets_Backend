package com.fathersprophets.backend.models.classes

import com.fathersprophets.backend.models.dto.ClassDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateClassRequest(
    val name: String? = null,
    val image: String? = null
) {
    fun toClassDto() = ClassDto(
        id = 0,
        name = this.name ?: "",
        image = this.image ?: ""
    )
}