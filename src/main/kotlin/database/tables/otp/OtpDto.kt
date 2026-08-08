package com.fathersprophets.backend.modules.otp

import com.fathersprophets.backend.database.enums.OtpType
import kotlinx.serialization.Serializable

@Serializable
data class OtpDto(
    val id: Int,
    val userId: Int,
    val type: OtpType,
    val otpCode: String?,
    val otpExpiresAt: String?,
    val pendingValue: String?,
    val resetTransactionId: String?,
    val resetVerifyToken: String?,
    val resetVerifyTokenExpiresAt: String?
)

@Serializable
data class OtpCreateDto(
    val userId: Int,
    val type: OtpType,
    val otpCode: String? = null,
    val otpExpiresAt: String? = null,
    val pendingValue: String? = null,
    val resetTransactionId: String? = null,
    val resetVerifyToken: String? = null,
    val resetVerifyTokenExpiresAt: String? = null
)

@Serializable
data class OtpUpdateDto(
    val userId: Int? = null,
    val type: OtpType? = null,
    val otpCode: String? = null,
    val otpExpiresAt: String? = null,
    val pendingValue: String? = null,
    val resetTransactionId: String? = null,
    val resetVerifyToken: String? = null,
    val resetVerifyTokenExpiresAt: String? = null
)