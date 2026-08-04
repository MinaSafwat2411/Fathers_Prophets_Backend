package com.fathersprophets.backend.modules.version

import kotlinx.serialization.Serializable

@Serializable
data class VersionDto(
    val id: Int,
    val version: String,
    val versionCode: Int,
    val adminPin: String
)

@Serializable
data class VersionCreateDto(
    val version: String,
    val versionCode: Int,
    val adminPin: String
)

@Serializable
data class VersionUpdateDto(
    val version: String? = null,
    val versionCode: Int? = null,
    val adminPin: String? = null
)