package com.fathersprophets.backend.database.repository.auth

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.request.auth.LoginRequest
import com.fathersprophets.backend.models.request.auth.RefreshRequest
import com.fathersprophets.backend.models.request.auth.RegisterRequest
import com.fathersprophets.backend.models.response.auth.LoginResponse
import com.fathersprophets.backend.models.response.auth.RefreshResponse

interface IAuthRepository {
    suspend fun register(request: RegisterRequest): ApiResponse<Nothing>
    suspend fun login(request: LoginRequest): ApiResponse<LoginResponse>

    suspend fun refreshToken(refresh: RefreshRequest): ApiResponse<RefreshResponse>

    suspend fun logout(userId: Int): ApiResponse<Nothing>
}
