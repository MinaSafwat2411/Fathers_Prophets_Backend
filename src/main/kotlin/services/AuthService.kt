package com.fathersprophets.backend.services

import com.fathersprophets.backend.database.repository.auth.IAuthRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.request.auth.LoginRequest
import com.fathersprophets.backend.models.request.auth.RegisterRequest
import com.fathersprophets.backend.models.response.auth.LoginResponse

class AuthService(private val authRepository: IAuthRepository) : IAuthService {
    override suspend fun register(request: RegisterRequest): ApiResponse<Nothing> {
        return authRepository.register(request)
    }

    override suspend fun login(request: LoginRequest): ApiResponse<LoginResponse> {
        return authRepository.login(request)
    }
}
