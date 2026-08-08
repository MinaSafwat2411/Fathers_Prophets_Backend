package com.fathersprophets.backend.modules.profile.repository

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.database.tables.user.UserDto
import com.fathersprophets.backend.modules.profile.models.ChangeContactOtpResponse
import com.fathersprophets.backend.modules.profile.models.ChangeEmailRequest
import com.fathersprophets.backend.modules.profile.models.ChangePasswordRequest
import com.fathersprophets.backend.modules.profile.models.ChangePhoneRequest
import com.fathersprophets.backend.modules.profile.models.VerifyContactOtpRequest

interface IProfileRepository {
    fun getProfile(userId: Int, lang: String): ApiResponse<UserDto>

    // Email change, step 1: the code goes to the *new* address, so verifying it proves the user owns it.
    fun requestEmailChange(userId: Int, dto: ChangeEmailRequest, lang: String): ApiResponse<ChangeContactOtpResponse>

    // Step 2: the new address is read back from the stored OTP row, never from the request body.
    fun verifyEmailChange(userId: Int, dto: VerifyContactOtpRequest, lang: String): ApiResponse<UserDto>

    // Phone change, step 1: same flow, but the code is delivered over WhatsApp.
    fun requestPhoneChange(userId: Int, dto: ChangePhoneRequest, lang: String): ApiResponse<ChangeContactOtpResponse>

    fun verifyPhoneChange(userId: Int, dto: VerifyContactOtpRequest, lang: String): ApiResponse<UserDto>

    fun changeProfileImage(userId: Int, imageUrl: String?, lang: String): ApiResponse<UserDto>

    fun changePassword(userId: Int, dto: ChangePasswordRequest, lang: String): ApiResponse<Nothing>
}