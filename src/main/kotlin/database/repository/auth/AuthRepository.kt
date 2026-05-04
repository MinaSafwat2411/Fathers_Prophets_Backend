package com.fathersprophets.backend.database.repository.auth

import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.exceptions.ConflictException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.users.User
import com.fathersprophets.backend.models.dto.auth.LoginRequest
import com.fathersprophets.backend.models.dto.auth.RefreshRequest
import com.fathersprophets.backend.models.dto.auth.RegisterRequest
import com.fathersprophets.backend.models.response.auth.LoginResponse
import com.fathersprophets.backend.models.response.auth.RefreshResponse
import com.fathersprophets.backend.models.dto.users.UserResponse
import com.fathersprophets.backend.utils.JwtConfig
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.PasswordUtil

class AuthRepository(
    val userDao: UserDao,
    private val lang: String = "en"
) : IAuthRepository {
    override suspend fun register(request: RegisterRequest): ApiResponse<Nothing> {
        val existingUser = userDao.findByUsername(request.username)
        if (existingUser != null) {
            throw ConflictException(Localization.get("username_exists", lang))
        }

        val passwordHash = PasswordUtil.hashPassword(request.password)

        userDao.createUser(
            User(
                id = 0,
                name = request.name,
                username = request.username,
                passwordHash = passwordHash,
                role = "member",
                isReviewed = false,
            )
        )

        return ApiResponse(success = true, message = Localization.get("register_success", lang))
    }

    override suspend fun login(request: LoginRequest): ApiResponse<LoginResponse> {

        val user = userDao.findByUsername(request.username)
            ?: return ApiResponse(false, Localization.get("invalid_credentials", lang))


        if (!PasswordUtil.checkPassword(request.password, user.passwordHash)) {
            return ApiResponse(false, Localization.get("invalid_credentials", lang))
        }

        val token = JwtConfig.generateAccessToken(
            user.id,
            user.username,
            user.role,
            user.isReviewed == true
        )
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
            birthDate = user.birthDate,
            fatherName = user.fatherName,
            isShams = user.isShams,
            profile = user.profile,
            isReviewed = user.isReviewed,
            memberId = user.memberId
        )

        return ApiResponse(
            success = true,
            message = Localization.get("login_success", lang),
            data = LoginResponse(
                user = userResponse,
                token = token,
                refreshToken = refreshToken
            )
        )
    }

    override suspend fun refreshToken(refresh: RefreshRequest): ApiResponse<RefreshResponse> {
        val userId = JwtConfig.verifyRefreshToken(refresh.refreshToken)
            ?: return ApiResponse(false, Localization.get("invalid_token", lang))

        val user = userDao.findById(userId)
            ?: return ApiResponse(false, Localization.get("user_not_found", lang))

        if (user.refreshToken != refresh.refreshToken) {
            return ApiResponse(false, Localization.get("invalid_token", lang))
        }

        val newToken = JwtConfig.generateAccessToken(
            user.id,
            user.username,
            user.role,
            user.isReviewed == true
        )
        val newRefreshToken = JwtConfig.generateRefreshToken(user.id)

        userDao.updateToken(user.id, newToken)
        userDao.updateRefreshToken(user.id, newRefreshToken)

        return ApiResponse(
            success = true,
            message = Localization.get("token_refreshed", lang),
            data = RefreshResponse(
                token = newToken,
                refreshToken = newRefreshToken
            )
        )
    }

    override suspend fun logout(userId: Int): ApiResponse<Nothing> {
        userDao.updateToken(userId, "")
        userDao.updateRefreshToken(userId, "")
        return ApiResponse(success = true, message = Localization.get("logout_success", lang))
    }

}
