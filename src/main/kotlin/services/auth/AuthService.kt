package com.fathersprophets.backend.services.auth

import com.fathersprophets.backend.database.repository.auth.AuthRepository
import com.fathersprophets.backend.database.repository.auth.IAuthRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.auth.LoginRequest
import com.fathersprophets.backend.models.auth.RefreshRequest
import com.fathersprophets.backend.models.auth.RegisterRequest
import com.fathersprophets.backend.models.auth.LoginResponse
import com.fathersprophets.backend.models.auth.RefreshResponse
import com.fathersprophets.backend.models.auth.RegisterResponse
import com.fathersprophets.backend.models.users.UserResponse
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

        return authRepository.register(request)
    }

    override suspend fun login(request: LoginRequest, lang: String): ApiResponse<LoginResponse> {
        validateRequired(
            request.username to "username",
            request.password to "password",
            lang = lang
        )

        return authRepository.login(request)
    }

    override suspend fun refreshToken(refreshRequest: RefreshRequest, lang: String): ApiResponse<RefreshResponse> {
        validateRequired(
            refreshRequest.refreshToken to "refresh_token",
            lang = lang
        )

        return authRepository.refreshToken(refreshRequest)
    }

    override suspend fun logout(userId: Int, lang: String): ApiResponse<Nothing> {

        return authRepository.logout(userId)
    }
}