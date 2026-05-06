package com.fathersprophets.backend.models.classes

import com.fathersprophets.backend.models.dto.ClassDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateClassRequest(
    val name: String? = null,
    val image: String? = null
) {
    fun toClassDto(id: Int) = ClassDto(
        id = id,
        name = this.name ?: "",
        image = this.image ?: ""
    )
}
