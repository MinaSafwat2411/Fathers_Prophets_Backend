package com.fathersprophets.backend.modules.profile.models

import kotlinx.serialization.Serializable

@Serializable
data class ChangeEmailRequest(val newEmail: String)

@Serializable
data class ChangePhoneRequest(val newPhone: String)

@Serializable
data class VerifyContactOtpRequest(val otpCode: String)

@Serializable
data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)