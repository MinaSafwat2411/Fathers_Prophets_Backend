package com.fathersprophets.backend.modules.auth.service

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

interface IAuthService {
    fun register(dto: RegisterRequest, lang: String): ApiResponse<AuthResponseResponse>

    fun verifyOtp(userId: Int, request: VerifyOtpRequest, lang: String): ApiResponse<AuthResponseResponse>
    fun login(dto: LoginRequest, lang: String): ApiResponse<AuthResponseResponse>
    fun refreshToken(userId: Int, refreshToken: String, lang: String): ApiResponse<RefreshTokenResponse>

    fun logout(userId: Int, lang: String): ApiResponse<Nothing>

    fun forgotPassword(dto: ForgotPasswordRequest, lang: String): ApiResponse<ForgotPasswordResponse>
    fun sendResetOtp(dto: SendResetOtpRequest, lang: String): ApiResponse<SendResetOtpResponse>
    fun resendResetOtp(dto: ResendResetOtpRequest, lang: String): ApiResponse<SendResetOtpResponse>
    fun verifyResetOtp(dto: VerifyResetOtpRequest, lang: String): ApiResponse<VerifyResetOtpResponse>
    fun resetPassword(dto: ResetPasswordRequest, lang: String): ApiResponse<Nothing>
}