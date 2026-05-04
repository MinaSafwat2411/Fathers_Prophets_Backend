package com.fathersprophets.backend.services.auth

import com.fathersprophets.backend.database.repository.auth.AuthRepository
import com.fathersprophets.backend.database.repository.auth.IAuthRepository
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.auth.LoginRequest
import com.fathersprophets.backend.models.dto.auth.RefreshRequest
import com.fathersprophets.backend.models.dto.auth.RegisterRequest
import com.fathersprophets.backend.models.response.auth.LoginResponse
import com.fathersprophets.backend.models.response.auth.RefreshResponse
import com.fathersprophets.backend.utils.Localization

class AuthService(private val authRepository: IAuthRepository) : IAuthService {
    override suspend fun register(request: RegisterRequest, lang: String): ApiResponse<Nothing> {
        if (request.username.isBlank()) {
            throw BadRequestException(Localization.get("username_empty", lang))
        }
        if (request.password.isBlank()) {
            throw BadRequestException(Localization.get("password_empty", lang))
        }
        val repo = if (authRepository is AuthRepository) {
            AuthRepository(authRepository.userDao, lang)
        } else {
            authRepository
        }
        return repo.register(request)
    }

    override suspend fun login(request: LoginRequest, lang: String): ApiResponse<LoginResponse> {
        if (request.username.isBlank()) {
            throw BadRequestException(Localization.get("username_empty", lang))
        }
        if (request.password.isBlank()) {
            throw BadRequestException(Localization.get("password_empty", lang))
        }
        val repo = if (authRepository is AuthRepository) {
            AuthRepository(authRepository.userDao, lang)
        } else {
            authRepository
        }
        return repo.login(request)
    }

    override suspend fun refreshToken(refresh: String, lang: String): ApiResponse<RefreshResponse> {
        val repo = if (authRepository is AuthRepository) {
            AuthRepository(authRepository.userDao, lang)
        } else {
            authRepository
        }

        return repo.refreshToken(RefreshRequest(refresh))
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
