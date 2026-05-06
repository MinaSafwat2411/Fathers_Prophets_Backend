package com.fathersprophets.backend.models.dto

data class VersionDto(
    val id: Int,
    val version: String,
    val adminPin: String
)