package com.fathersprophets.backend.models.version

import com.fathersprophets.backend.database.dto.VersionDto
import kotlinx.serialization.Serializable

@Serializable
data class VersionRequest(
    val version : String? = null,
    val adminPin : String? = null
){
    fun toVersionDto() = VersionDto(
        id = 0,
        version = version ?: "",
        adminPin = adminPin ?: ""
    )

}
