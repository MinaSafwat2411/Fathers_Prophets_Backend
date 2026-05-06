package com.fathersprophets.backend.database.repository.auth

import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.exceptions.ConflictException
import com.fathersprophets.backend.exceptions.UnauthorizedException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.auth.*
import com.fathersprophets.backend.models.dto.UserDto
import com.fathersprophets.backend.utils.JwtConfig
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.PasswordUtil

class AuthRepository(
    val userDao: UserDao,
    private val lang: String = "en"
) : IAuthRepository {
    override suspend fun register(request: RegisterRequest): ApiResponse<RegisterResponse> {
        val passwordHash = PasswordUtil.hashPassword(request.password ?: "")

        val existingUser = userDao.findByUsername(request.toUserDto(passwordHash))
        if (existingUser != null) {
            throw ConflictException(Localization.get("username_exists", lang))
        }

        val newUserId = userDao.createUser(
            request.toUserDto(passwordHash)
        )

        var user = userDao.findById(request.toUserDto(passwordHash).copy(id = newUserId)) ?: throw ConflictException(
            Localization.get("register_failed", lang)
        )

        val token = generateAccessToken(user)
        val refreshToken = generateRefresh(user)

        user = user.copy(
            token = token,
            refreshToken = refreshToken,
        )

        userDao.updateToken(user)
        userDao.updateRefreshToken(user)

        return ApiResponse(
            success = true,
            message = Localization.get("register_success", lang),
            data = RegisterResponse(
                user = user.convertToUserResponse(),
                token = token,
                refreshToken = refreshToken
            )
        )
    }

    override suspend fun login(request: LoginRequest): ApiResponse<LoginResponse> {

        val hashPassword = PasswordUtil.hashPassword(request.password ?: "")

        var user = userDao.findByUsername(request.toUserDto(hashPassword))
            ?: throw ConflictException(Localization.get("user_not_found", lang))


        if (!PasswordUtil.checkPassword(request.password ?: "", user.passwordHash)) {
            throw ConflictException(Localization.get("invalid_credentials", lang))
        }

        val token = JwtConfig.generateAccessToken(
            user.id,
            user.username,
            user.role,
            user.isReviewed == true
        )
        val refreshToken = JwtConfig.generateRefreshToken(user.id)

        user = user.copy(
            token = token,
            refreshToken = refreshToken
        )

        userDao.updateToken(user)
        userDao.updateRefreshToken(user)
        userDao.updateFcmToken(user)

        return ApiResponse(
            success = true,
            message = Localization.get("login_success", lang),
            data = LoginResponse(
                user = user.convertToUserResponse(),
                token = token,
                refreshToken = refreshToken
            )
        )
    }

    override suspend fun refreshToken(refresh: RefreshRequest): ApiResponse<RefreshResponse> {
        val userId = JwtConfig.verifyRefreshToken(refresh.refreshToken ?: "")
            ?: throw UnauthorizedException(Localization.get("invalid_token", lang))

        var user = userDao.findById(refresh.toUserDto().copy(id = userId))
            ?: throw ConflictException(Localization.get("user_not_found", lang))

        val newToken = generateAccessToken(user)
        val newRefreshToken = generateRefresh(user)

        user = user.copy(
            token = newToken,
            refreshToken = newRefreshToken
        )

        userDao.updateToken(user)
        userDao.updateRefreshToken(user)

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
        val userDto = idToUser(userId).copy(
            token = null,
            refreshToken = null,
            fcmToken = null
        )
        userDao.updateToken(userDto)
        userDao.updateRefreshToken(userDto)
        userDao.updateFcmToken(userDto)
        return ApiResponse(success = true, message = Localization.get("logout_success", lang))
    }

    private fun generateAccessToken(userDto: UserDto): String {
        return JwtConfig.generateAccessToken(
            userDto.id,
            userDto.username,
            userDto.role,
            userDto.isReviewed == true
        )
    }

    private fun generateRefresh(userDto: UserDto): String {
        return JwtConfig.generateRefreshToken(userDto.id)
    }

    private fun idToUser(id: Int): UserDto {
        return UserDto(
            id = id,
            name = "",
            username = "",
            passwordHash = "",
            role = "",
        )
    }

}
