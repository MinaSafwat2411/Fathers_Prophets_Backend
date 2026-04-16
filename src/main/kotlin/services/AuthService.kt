package com.fathersprophets.backend.services

import com.fathersprophets.backend.database.repository.auth.AuthRepository
import com.fathersprophets.backend.database.repository.auth.IAuthRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.request.auth.LoginRequest
import com.fathersprophets.backend.models.request.auth.RegisterRequest
import com.fathersprophets.backend.models.response.auth.LoginResponse

class AuthService(private val authRepository: IAuthRepository) : IAuthService {
    override suspend fun register(request: RegisterRequest, lang: String): ApiResponse<Nothing> {
        val repo = if (authRepository is AuthRepository) {
            AuthRepository(authRepository.userDao, lang)
        } else {
            authRepository
        }
        return repo.register(request)
    }

    override suspend fun login(request: LoginRequest, lang: String): ApiResponse<LoginResponse> {
        val repo = if (authRepository is AuthRepository) {
            AuthRepository(authRepository.userDao, lang)
        } else {
            authRepository
        }
        return repo.login(request)
    }
}
