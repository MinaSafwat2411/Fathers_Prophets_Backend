package com.fathersprophets.backend.database.repository.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.database.tables.UsersTable
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.request.auth.LoginRequest
import com.fathersprophets.backend.models.request.auth.RegisterRequest
import com.fathersprophets.backend.models.response.auth.LoginResponse
import com.fathersprophets.backend.models.response.users.UserResponse
import com.fathersprophets.backend.utils.PasswordUtil

class AuthRepository(
    private val userDao: UserDao,
    private val jwtSecret: String,
    private val jwtIssuer: String,
    private val jwtAudience: String
) : IAuthRepository {
    override suspend fun register(request: RegisterRequest): ApiResponse<Nothing> {
        val existingUser = userDao.findByUsername(request.username)
        if (existingUser != null) {
            return ApiResponse(success = false, message = "Username already exists")
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
        val userRow = userDao.findByUsername(request.username)
            ?: return ApiResponse(success = false, message = "Invalid username or password")

        val passwordHash = userRow[UsersTable.passwordHash]
        if (!PasswordUtil.checkPassword(request.password, passwordHash)) {
            return ApiResponse(success = false, message = "Invalid username or password")
        }

        val userResponse = UserResponse(
            id = userRow[UsersTable.id],
            name = userRow[UsersTable.name],
            username = userRow[UsersTable.username],
            role = userRow[UsersTable.role],
            email = userRow[UsersTable.email],
            phone = userRow[UsersTable.phone],
            address = userRow[UsersTable.address],
            birthDate = userRow[UsersTable.birthDate]?.toString(),
            fatherName = userRow[UsersTable.fatherName],
            isShams = userRow[UsersTable.isShams],
            profile = userRow[UsersTable.profile],
            isReviewed = userRow[UsersTable.isReviewed],
            classId = userRow[UsersTable.classId],
            memberId = userRow[UsersTable.memberId]
        )

        val algorithm = Algorithm.HMAC256(jwtSecret)
        val token = JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withClaim("username", userRow[UsersTable.username])
            .withClaim("userId", userRow[UsersTable.id])
            .sign(algorithm)

        val refreshToken = JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withClaim("userId", userRow[UsersTable.id])
            .sign(algorithm)

        userDao.updateToken(userRow[UsersTable.id], token)

        userDao.updateRefreshToken(userRow[UsersTable.id], refreshToken)

        userDao.updateFcmToken(userRow[UsersTable.id], request.fcmToken)

        val loginResponse = LoginResponse(
            user = userResponse,
            token = token,
            refreshToken = refreshToken
        )

        return ApiResponse(
            success = true,
            message = "Login successful",
            data = loginResponse
        )
    }
}
