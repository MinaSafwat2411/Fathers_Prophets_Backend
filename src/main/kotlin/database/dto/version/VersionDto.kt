package com.fathersprophets.backend.database.dto.version

data class VersionDto(
    val id: Int,
    val version: String,
    val adminPin: String,
    val versionCode: Int
)