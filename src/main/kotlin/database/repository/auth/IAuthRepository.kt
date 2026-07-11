package com.fathersprophets.backend.database.repository.auth

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.auth.LoginRequest
import com.fathersprophets.backend.models.auth.RefreshRequest
import com.fathersprophets.backend.models.auth.RegisterRequest
import com.fathersprophets.backend.models.auth.LoginResponse
import com.fathersprophets.backend.models.auth.RefreshResponse
import com.fathersprophets.backend.models.auth.RegisterResponse

interface IAuthRepository {
    suspend fun register(request: RegisterRequest, lang: String): ApiResponse<RegisterResponse>
    suspend fun login(request: LoginRequest, lang: String): ApiResponse<LoginResponse>

    suspend fun refreshToken(refresh: RefreshRequest, lang: String): ApiResponse<RefreshResponse>

    suspend fun logout(userId: Int, lang: String): ApiResponse<Nothing>
}
