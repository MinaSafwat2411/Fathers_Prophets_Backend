package com.fathersprophets.backend.modules.auth.repository

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.modules.auth.models.AuthResponseResponse
import com.fathersprophets.backend.modules.auth.models.ForgotPasswordRequest
import com.fathersprophets.backend.modules.auth.models.ForgotPasswordResponse
import com.fathersprophets.backend.modules.auth.models.LoginRequest
import com.fathersprophets.backend.modules.auth.models.RefreshTokenResponse
import com.fathersprophets.backend.modules.auth.models.RegisterRequest
import com.fathersprophets.backend.modules.auth.models.ResendResetOtpRequest
import com.fathersprophets.backend.modules.auth.models.ResetPasswordRequest
import com.fathersprophets.backend.modules.auth.models.SendResetOtpRequest
import com.fathersprophets.backend.modules.auth.models.SendResetOtpResponse
import com.fathersprophets.backend.modules.auth.models.VerifyOtpRequest
import com.fathersprophets.backend.modules.auth.models.VerifyResetOtpRequest
import com.fathersprophets.backend.modules.auth.models.VerifyResetOtpResponse

interface IAuthRepository {
    fun register(dto: RegisterRequest, lang: String): ApiResponse<AuthResponseResponse>

    fun verifyOtp(userId: Int, request: VerifyOtpRequest, lang: String): ApiResponse<AuthResponseResponse>
    fun login(dto: LoginRequest, lang: String): ApiResponse<AuthResponseResponse>
    fun refreshToken(userId: Int, refreshToken: String, lang: String): ApiResponse<RefreshTokenResponse>

    fun logout(userId: Int, lang: String): ApiResponse<Nothing>

    // Step 1: user submits their username; we confirm the account exists and hand back a masked email.
    fun forgotPassword(dto: ForgotPasswordRequest, lang: String): ApiResponse<ForgotPasswordResponse>

    // Step 2: user types the real email back; if it matches the account, an OTP is emailed to it.
    fun sendResetOtp(dto: SendResetOtpRequest, lang: String): ApiResponse<SendResetOtpResponse>

    fun resendResetOtp(dto: ResendResetOtpRequest, lang: String): ApiResponse<SendResetOtpResponse>

    // Step 3: user submits the OTP; on success we hand back a short-lived token authorizing the password change.
    fun verifyResetOtp(dto: VerifyResetOtpRequest, lang: String): ApiResponse<VerifyResetOtpResponse>

    // Step 4: user submits the new password along with that token.
    fun resetPassword(dto: ResetPasswordRequest, lang: String): ApiResponse<Nothing>
}