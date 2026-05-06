package com.fathersprophets.backend.services.auth

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.auth.LoginRequest
import com.fathersprophets.backend.models.auth.RefreshRequest
import com.fathersprophets.backend.models.auth.RegisterRequest
import com.fathersprophets.backend.models.auth.LoginResponse
import com.fathersprophets.backend.models.auth.RefreshResponse
import com.fathersprophets.backend.models.auth.RegisterResponse

interface IAuthService {
    suspend fun register(request: RegisterRequest, lang: String = "en"): ApiResponse<RegisterResponse>
    suspend fun login(request: LoginRequest, lang: String = "en"): ApiResponse<LoginResponse>

    suspend fun refreshToken(refreshRequest: RefreshRequest, lang: String = "en"): ApiResponse<RefreshResponse>

    suspend fun logout(userId: Int, lang: String = "en"): ApiResponse<Nothing>
}