package com.fathersprophets.backend.modules.token

import kotlinx.serialization.Serializable

@Serializable
data class TokenDto(
    val id: Int,
    val userId: Int,
    val token: String,
    val refreshToken: String,
    val expiresAt: Long,
    val fcmToken: String?,
    val adminToken: String?
)

@Serializable
data class TokenCreateDto(
    val userId: Int,
    val token: String,
    val refreshToken: String,
    val expiresAt: Long,
    val fcmToken: String? = null,
    val adminToken: String? = null
)

@Serializable
data class TokenUpdateDto(
    val userId: Int? = null,
    val token: String? = null,
    val refreshToken: String? = null,
    val expiresAt: Long? = null,
    val fcmToken: String? = null,
    val adminToken: String? = null
)