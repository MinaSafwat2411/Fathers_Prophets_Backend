package com.fathersprophets.backend.database.repository.auth

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.auth.LoginRequest
import com.fathersprophets.backend.models.auth.RefreshRequest
import com.fathersprophets.backend.models.auth.RegisterRequest
import com.fathersprophets.backend.models.auth.LoginResponse
import com.fathersprophets.backend.models.auth.RefreshResponse
import com.fathersprophets.backend.models.auth.RegisterResponse
import com.fathersprophets.backend.models.auth.ForgotPasswordRequest
import com.fathersprophets.backend.models.auth.ForgotPasswordResponse
import com.fathersprophets.backend.models.auth.SendOtpRequest
import com.fathersprophets.backend.models.auth.SendOtpResponse
import com.fathersprophets.backend.models.auth.ResendOtpRequest
import com.fathersprophets.backend.models.auth.VerifyOtpRequest
import com.fathersprophets.backend.models.auth.VerifyOtpResponse
import com.fathersprophets.backend.models.auth.ResetPasswordRequest

interface IAuthRepository {
    suspend fun register(request: RegisterRequest, lang: String): ApiResponse<RegisterResponse>
    suspend fun login(request: LoginRequest, lang: String): ApiResponse<LoginResponse>

    suspend fun refreshToken(refresh: RefreshRequest, lang: String): ApiResponse<RefreshResponse>

    suspend fun logout(userId: Int, lang: String): ApiResponse<Nothing>

    suspend fun forgotPassword(request: ForgotPasswordRequest, lang: String): ApiResponse<ForgotPasswordResponse>
    suspend fun sendOtp(request: SendOtpRequest, lang: String): ApiResponse<SendOtpResponse>
    suspend fun resendOtp(request: ResendOtpRequest, lang: String): ApiResponse<SendOtpResponse>
    suspend fun verifyOtp(request: VerifyOtpRequest, lang: String): ApiResponse<VerifyOtpResponse>
    suspend fun resetPassword(request: ResetPasswordRequest, lang: String): ApiResponse<Nothing>
}
