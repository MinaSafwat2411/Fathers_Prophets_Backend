package com.fathersprophets.backend.services.auth

import com.fathersprophets.backend.database.repository.auth.IAuthRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.auth.*
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class AuthService(
    private val authRepository: IAuthRepository
) : IAuthService {
    override suspend fun register(request: RegisterRequest, lang: String): ApiResponse<RegisterResponse> {
        validateRequired(
            request.username to "username",
            request.password to "password",
            request.name to "name",
            lang = lang
        )

        return authRepository.register(request, lang)
    }

    override suspend fun login(request: LoginRequest, lang: String): ApiResponse<LoginResponse> {
        validateRequired(
            request.username to "username",
            request.password to "password",
            lang = lang
        )

        return authRepository.login(request, lang)
    }

    override suspend fun refreshToken(refreshRequest: RefreshRequest, lang: String): ApiResponse<RefreshResponse> {
        validateRequired(
            refreshRequest.refreshToken to "refresh_token",
            lang = lang
        )

        return authRepository.refreshToken(refreshRequest, lang)
    }

    override suspend fun logout(userId: Int, lang: String): ApiResponse<Nothing> {

        return authRepository.logout(userId, lang)
    }

    override suspend fun forgotPassword(request: ForgotPasswordRequest, lang: String): ApiResponse<ForgotPasswordResponse> {
        validateRequired(
            request.username to "username",
            lang = lang
        )

        return authRepository.forgotPassword(request, lang)
    }

    override suspend fun sendOtp(request: SendOtpRequest, lang: String): ApiResponse<SendOtpResponse> {
        validateRequired(
            request.username to "username",
            request.email to "email",
            lang = lang
        )

        return authRepository.sendOtp(request, lang)
    }

    override suspend fun resendOtp(request: ResendOtpRequest, lang: String): ApiResponse<SendOtpResponse> {
        validateRequired(
            request.transactionId to "transaction_id",
            lang = lang
        )

        return authRepository.resendOtp(request, lang)
    }

    override suspend fun verifyOtp(request: VerifyOtpRequest, lang: String): ApiResponse<VerifyOtpResponse> {
        validateRequired(
            request.transactionId to "transaction_id",
            request.otp to "otp",
            lang = lang
        )

        return authRepository.verifyOtp(request, lang)
    }

    override suspend fun resetPassword(request: ResetPasswordRequest, lang: String): ApiResponse<Nothing> {
        validateRequired(
            request.verifyToken to "verify_token",
            request.newPassword to "new_password",
            lang = lang
        )

        return authRepository.resetPassword(request, lang)
    }
}