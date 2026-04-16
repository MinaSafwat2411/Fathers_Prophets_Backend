package com.fathersprophets.backend.database.repository.auth

import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.exceptions.ConflictException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.request.auth.LoginRequest
import com.fathersprophets.backend.models.request.auth.RegisterRequest
import com.fathersprophets.backend.models.response.auth.LoginResponse
import com.fathersprophets.backend.models.response.users.UserResponse
import com.fathersprophets.backend.utils.JwtConfig
import com.fathersprophets.backend.utils.PasswordUtil

class AuthRepository(
    private val userDao: UserDao
) : IAuthRepository {
    override suspend fun register(request: RegisterRequest): ApiResponse<Nothing> {
        val existingUser = userDao.findByUsername(request.username)
        if (existingUser != null) {
            throw ConflictException("Username already exists")
        }

        val passwordHash = PasswordUtil.hashPassword(request.password)

        userDao.createUser(
            mapOf(
                "name" to request.name,
                "username" to request.username,
                "password_hash" to passwordHash,
                "role" to "member",
                "is_reviewed" to false
            )
        )

        return ApiResponse(success = true, message = "Registration successful")
    }

    override suspend fun login(request: LoginRequest): ApiResponse<LoginResponse> {

        val user = userDao.findByUsername(request.username)
            ?: return ApiResponse(false, "Invalid username or password")


        if (!PasswordUtil.checkPassword(request.password, user.passwordHash)) {
            return ApiResponse(false, "Invalid username or password")
        }

        val token = JwtConfig.generateAccessToken(user.id, user.username)
        val refreshToken = JwtConfig.generateRefreshToken(user.id)

        userDao.updateToken(user.id, token)
        userDao.updateRefreshToken(user.id, refreshToken)

        userDao.updateFcmToken(user.id, request.fcmToken)

        val userResponse = UserResponse(
            id = user.id,
            name = user.name,
            username = user.username,
            role = user.role,
            email = user.email,
            phone = user.phone,
            address = user.address,
            birthDate = user.birthDate?.toString(),
            fatherName = user.fatherName,
            isShams = user.isShams,
            profile = user.profile,
            isReviewed = user.isReviewed,
            classId = user.classId,
            memberId = user.memberId
        )

        return ApiResponse(
            success = true,
            message = "Login successful",
            data = LoginResponse(
                user = userResponse,
                token = token,
                refreshToken = refreshToken
            )
        )
    }
}
