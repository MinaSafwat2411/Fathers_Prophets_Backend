package com.fathersprophets.backend.database.repository.auth

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.request.auth.LoginRequest
import com.fathersprophets.backend.models.request.auth.RegisterRequest
import com.fathersprophets.backend.models.response.auth.LoginResponse

interface IAuthRepository {
    suspend fun register(request: RegisterRequest): ApiResponse<Nothing>
    suspend fun login(request: LoginRequest): ApiResponse<LoginResponse>
}
