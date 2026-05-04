package com.fathersprophets.backend.services.auth

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.auth.LoginRequest
import com.fathersprophets.backend.models.dto.auth.RegisterRequest
import com.fathersprophets.backend.models.response.auth.LoginResponse
import com.fathersprophets.backend.models.response.auth.RefreshResponse

interface IAuthService {
    suspend fun register(request: RegisterRequest, lang: String = "en"): ApiResponse<Nothing>
    suspend fun login(request: LoginRequest, lang: String = "en"): ApiResponse<LoginResponse>

    suspend fun refreshToken(refresh: String, lang: String = "en"): ApiResponse<RefreshResponse>

    suspend fun logout(userId: Int, lang: String = "en"): ApiResponse<Nothing>
}