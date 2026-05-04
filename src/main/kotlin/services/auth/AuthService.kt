package com.fathersprophets.backend.services.auth

import com.fathersprophets.backend.database.repository.auth.AuthRepository
import com.fathersprophets.backend.database.repository.auth.IAuthRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.auth.LoginRequest
import com.fathersprophets.backend.models.dto.auth.RefreshRequest
import com.fathersprophets.backend.models.dto.auth.RegisterRequest
import com.fathersprophets.backend.models.response.auth.LoginResponse
import com.fathersprophets.backend.models.response.auth.RefreshResponse
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class AuthService(private val authRepository: IAuthRepository) : IAuthService {
    override suspend fun register(request: RegisterRequest, lang: String): ApiResponse<Nothing> {
        validateRequired(
            request.username to "username",
            request.password to "password",
            request.name to "name",
            lang = lang
        )

        val repo = if (authRepository is AuthRepository) {
            AuthRepository(authRepository.userDao, lang)
        } else {
            authRepository
        }
        return repo.register(request)
    }

    override suspend fun login(request: LoginRequest, lang: String): ApiResponse<LoginResponse> {
        validateRequired(
            request.username to "username",
            request.password to "password",
            request.fcmToken to "fcm_token",
            lang = lang
        )

        val repo = if (authRepository is AuthRepository) {
            AuthRepository(authRepository.userDao, lang)
        } else {
            authRepository
        }
        return repo.login(request)
    }

    override suspend fun refreshToken(refreshRequest: RefreshRequest, lang: String): ApiResponse<RefreshResponse> {
        validateRequired(refreshRequest.refreshToken to "refresh_token", lang = lang)

        val repo = if (authRepository is AuthRepository) {
            AuthRepository(authRepository.userDao, lang)
        } else {
            authRepository
        }

        return repo.refreshToken(refreshRequest)
    }

    override suspend fun logout(userId: Int, lang: String): ApiResponse<Nothing> {
        val repo = if (authRepository is AuthRepository) {
            AuthRepository(authRepository.userDao, lang)
        } else {
            authRepository
        }

        return repo.logout(userId)
    }
}
