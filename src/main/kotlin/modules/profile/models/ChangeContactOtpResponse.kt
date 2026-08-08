package com.fathersprophets.backend.modules.profile.models

import kotlinx.serialization.Serializable

/**
 * Returned after an email/phone change OTP is issued. [maskedTarget] is the destination the code
 * was sent to, masked so it can be shown back to the user without echoing the full value.
 */
@Serializable
data class ChangeContactOtpResponse(
    val maskedTarget: String,
    val expiresInSeconds: Long
)