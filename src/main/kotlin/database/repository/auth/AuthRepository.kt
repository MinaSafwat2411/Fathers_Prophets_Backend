package com.fathersprophets.backend.database.repository.auth

import com.fathersprophets.backend.database.dao.users.UserDao
import com.fathersprophets.backend.database.tables.users.UserRole
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.ConflictException
import com.fathersprophets.backend.exceptions.UnauthorizedException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.auth.*
import com.fathersprophets.backend.models.dto.UserDto
import com.fathersprophets.backend.utils.JwtConfig
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.MailSender
import com.fathersprophets.backend.utils.PasswordUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.util.UUID

class AuthRepository(
    val userDao: UserDao,
) : IAuthRepository {
    override suspend fun register(request: RegisterRequest, lang: String): ApiResponse<RegisterResponse> {
        val passwordHash = PasswordUtil.hashPassword(request.password ?: "")

        val existingUser = userDao.findByUsername(request.username?:"")
        if (existingUser != null) {
            throw ConflictException(Localization.get("username_exists", lang))
        }

        val newUser = userDao.createUser(
            request.toUserDto(passwordHash)
        )?: throw UnauthorizedException(Localization.get("register_failed", lang))

        var user = userDao.findById(newUser.id) ?: throw ConflictException(
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

    override suspend fun login(request: LoginRequest, lang: String): ApiResponse<LoginResponse> {

        val hashPassword = PasswordUtil.hashPassword(request.password ?: "")

        var user = userDao.findByUsername(request.username ?: "")
            ?: throw ConflictException(Localization.get("user_not_found", lang))


        if (!PasswordUtil.checkPassword(request.password ?: "", user.passwordHash)) {
            throw ConflictException(Localization.get("invalid_credentials", lang))
        }

        val token = JwtConfig.generateAccessToken(
            user.id,
            user.username,
            user.role.name,
            user.isReviewed == true,
            user.name,
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
                accessToken = token,
                refreshToken = refreshToken
            )
        )
    }

    override suspend fun refreshToken(refresh: RefreshRequest, lang: String): ApiResponse<RefreshResponse> {
        val userId = JwtConfig.verifyRefreshToken(refresh.refreshToken ?: "")
            ?: throw UnauthorizedException(Localization.get("invalid_token", lang))

        var user = userDao.findById(userId)
            ?: throw ConflictException(Localization.get("user_not_found", lang))

        if (user.refreshToken == null || user.refreshToken != refresh.refreshToken) {
            throw UnauthorizedException(Localization.get("invalid_token", lang))
        }

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

    override suspend fun logout(userId: Int, lang: String): ApiResponse<Nothing> {
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

    override suspend fun forgotPassword(request: ForgotPasswordRequest, lang: String): ApiResponse<ForgotPasswordResponse> {
        val user = userDao.findByUsername(request.username ?: "")
            ?: throw ConflictException(Localization.get("user_not_found", lang))

        if (user.email.isNullOrBlank()) {
            throw RuntimeException(Localization.get("contact_admin_no_email", lang))
        }

        return ApiResponse(
            success = true,
            message = Localization.get("username_found", lang),
            data = ForgotPasswordResponse(maskedEmail = maskEmail(user.email))
        )
    }

    override suspend fun sendOtp(request: SendOtpRequest, lang: String): ApiResponse<SendOtpResponse> {
        val user = userDao.findByUsername(request.username ?: "")
            ?: throw ConflictException(Localization.get("user_not_found", lang))

        if (user.email.isNullOrBlank() || !user.email.equals(request.email, ignoreCase = true)) {
            throw BadRequestException(Localization.get("email_not_matched", lang))
        }

        val transactionId = UUID.randomUUID().toString()
        issueAndSendOtp(user.copy(resetTransactionId = transactionId))

        return ApiResponse(
            success = true,
            message = Localization.get("otp_sent", lang),
            data = SendOtpResponse(transactionId = transactionId)
        )
    }

    override suspend fun resendOtp(request: ResendOtpRequest, lang: String): ApiResponse<SendOtpResponse> {
        val user = userDao.findByResetTransactionId(request.transactionId ?: "")
            ?: throw BadRequestException(Localization.get("invalid_transaction_id", lang))

        issueAndSendOtp(user)

        return ApiResponse(
            success = true,
            message = Localization.get("otp_sent", lang),
            data = SendOtpResponse(transactionId = user.resetTransactionId)
        )
    }

    private suspend fun issueAndSendOtp(user: UserDto) {
        val otp = (100000..999999).random().toString()
        val expiresAt = Instant.now().plusSeconds(600)

        userDao.updateResetOtp(user.copy(otpCode = otp, otpExpiresAt = expiresAt))

        withContext(Dispatchers.IO) {
            MailSender.sendOtpEmail(user.email ?: "", otp)
        }
    }

    override suspend fun verifyOtp(request: VerifyOtpRequest, lang: String): ApiResponse<VerifyOtpResponse> {
        val user = userDao.findByResetTransactionId(request.transactionId ?: "")
            ?: throw BadRequestException(Localization.get("invalid_transaction_id", lang))

        if (user.otpCode == null || user.otpCode != request.otp) {
            throw BadRequestException(Localization.get("otp_invalid", lang))
        }

        if (user.otpExpiresAt == null || user.otpExpiresAt.isBefore(Instant.now())) {
            throw BadRequestException(Localization.get("otp_expired", lang))
        }

        val verifyToken = UUID.randomUUID().toString()
        val verifyTokenExpiresAt = Instant.now().plusSeconds(600)

        userDao.updateResetVerifyToken(
            user.copy(resetVerifyToken = verifyToken, resetVerifyTokenExpiresAt = verifyTokenExpiresAt)
        )

        return ApiResponse(
            success = true,
            message = Localization.get("otp_verified_successfully", lang),
            data = VerifyOtpResponse(verifyToken = verifyToken)
        )
    }

    override suspend fun resetPassword(request: ResetPasswordRequest, lang: String): ApiResponse<Nothing> {
        val user = userDao.findByResetVerifyToken(request.verifyToken ?: "")
            ?: throw UnauthorizedException(Localization.get("invalid_or_expired_token", lang))

        if (user.resetVerifyTokenExpiresAt == null || user.resetVerifyTokenExpiresAt.isBefore(Instant.now())) {
            throw UnauthorizedException(Localization.get("invalid_or_expired_token", lang))
        }

        val passwordHash = PasswordUtil.hashPassword(request.newPassword ?: "")
        userDao.updatePassword(user.copy(passwordHash = passwordHash))
        userDao.clearResetVerifyToken(user)

        return ApiResponse(success = true, message = Localization.get("password_updated_successfully", lang))
    }

    private fun maskEmail(email: String): String {
        val at = email.indexOf('@')
        if (at <= 1) return email
        return "${email.take(2)}***${email.substring(at)}"
    }

    private fun generateAccessToken(userDto: UserDto): String {
        return JwtConfig.generateAccessToken(
            userDto.id,
            userDto.username,
            userDto.role.name,
            userDto.isReviewed == true,
            userDto.name
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
            role = UserRole.member,
        )
    }

}
