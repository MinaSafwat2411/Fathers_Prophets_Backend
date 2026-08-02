package com.fathersprophets.backend.database.dto

data class VersionDto(
    val id: Int,
    val version: String,
    val adminPin: String,
    val versionCode: Int
)