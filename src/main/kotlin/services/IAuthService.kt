package com.fathersprophets.backend.services

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.request.auth.LoginRequest
import com.fathersprophets.backend.models.request.auth.RegisterRequest
import com.fathersprophets.backend.models.response.auth.LoginResponse

interface IAuthService {
    suspend fun register(request: RegisterRequest): ApiResponse<Nothing>
    suspend fun login(request: LoginRequest): ApiResponse<LoginResponse>
}
