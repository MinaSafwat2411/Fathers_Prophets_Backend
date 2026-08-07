package com.fathersprophets.backend.modules.auth.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.base.BaseService
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
import com.fathersprophets.backend.modules.auth.repository.AuthRepository
import com.fathersprophets.backend.modules.user.UserCreateDto
import com.fathersprophets.backend.modules.user.UserDto
import com.fathersprophets.backend.modules.user.UserUpdateDto
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class AuthService(
    authRepository: AuthRepository
) : BaseService<UserDto, UserCreateDto, UserUpdateDto, AuthRepository>(authRepository), IAuthService {

    override fun register(dto: RegisterRequest, lang: String): ApiResponse<AuthResponseResponse> {
        validateRequired(
            dto.firstName to "first_name",
            dto.lastName to "last_name",
            dto.username to "username",
            dto.email to "email",
            dto.password to "password",
            dto.fcmToken to "fcm_token",
            dto.birthDate to "birth_date",
            lang = lang
        )
        return repository.register(dto, lang)
    }

    override fun login(dto: LoginRequest, lang: String): ApiResponse<AuthResponseResponse> {
        validateRequired(
            dto.username to "username",
            dto.password to "password",
            dto.fcmToken to "fcm_token",
            lang = lang
        )
        return repository.login(dto, lang)
    }

    override fun verifyOtp(userId: Int, request: VerifyOtpRequest, lang: String): ApiResponse<AuthResponseResponse> {
        validateRequired(
            userId to "user_id",
            request.otpCode to "otp_code",
            request.fcmToken to "fcm_token",
            lang = lang
        )
        return repository.verifyOtp(userId, request, lang)
    }

    override fun refreshToken(userId: Int, refreshToken: String, lang: String): ApiResponse<RefreshTokenResponse> {
        validateRequired(
            userId to "user_id",
            refreshToken to "refresh_token",
            lang = lang
        )
        return repository.refreshToken(userId, refreshToken, lang)
    }

    override fun logout(userId: Int, lang: String): ApiResponse<Nothing> {
        validateRequired(userId to "user_id", lang = lang)
        return repository.logout(userId, lang)
    }

    override fun forgotPassword(dto: ForgotPasswordRequest, lang: String): ApiResponse<ForgotPasswordResponse> {
        validateRequired(dto.username to "username", lang = lang)
        return repository.forgotPassword(dto, lang)
    }

    override fun sendResetOtp(dto: SendResetOtpRequest, lang: String): ApiResponse<SendResetOtpResponse> {
        validateRequired(
            dto.username to "username",
            dto.email to "email",
            lang = lang
        )
        return repository.sendResetOtp(dto, lang)
    }

    override fun resendResetOtp(dto: ResendResetOtpRequest, lang: String): ApiResponse<SendResetOtpResponse> {
        validateRequired(dto.transactionId to "transaction_id", lang = lang)
        return repository.resendResetOtp(dto, lang)
    }

    override fun verifyResetOtp(dto: VerifyResetOtpRequest, lang: String): ApiResponse<VerifyResetOtpResponse> {
        validateRequired(
            dto.transactionId to "transaction_id",
            dto.otpCode to "otp_code",
            lang = lang
        )
        return repository.verifyResetOtp(dto, lang)
    }

    override fun resetPassword(dto: ResetPasswordRequest, lang: String): ApiResponse<Nothing> {
        validateRequired(
            dto.resetVerifyToken to "reset_verify_token",
            dto.newPassword to "new_password",
            lang = lang
        )
        return repository.resetPassword(dto, lang)
    }
}