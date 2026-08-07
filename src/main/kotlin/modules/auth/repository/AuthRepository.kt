package com.fathersprophets.backend.modules.auth.repository

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.base.BaseRepository
import com.fathersprophets.backend.database.enums.OtpType
import com.fathersprophets.backend.database.enums.UserRole
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.ConflictException
import com.fathersprophets.backend.exceptions.ForbiddenException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.exceptions.UnauthorizedException
import com.fathersprophets.backend.modules.auth.models.AuthResponseResponse
import com.fathersprophets.backend.modules.auth.models.ForgotPasswordRequest
import com.fathersprophets.backend.modules.auth.models.ForgotPasswordResponse
import com.fathersprophets.backend.modules.auth.models.LoginRequest
import com.fathersprophets.backend.modules.auth.models.RefreshTokenResponse
import com.fathersprophets.backend.modules.auth.models.RegisterRequest
import com.fathersprophets.backend.modules.auth.models.ResendResetOtpRequest
import com.fathersprophets.backend.modules.auth.models.ResetPasswordRequest
import com.fathersprophets.backend.modules.auth.models.SendResetOtpRequest
import com.fathersprophets.backend.modules.auth.models.SendResetOtpResponse
import com.fathersprophets.backend.modules.auth.models.VerifyOtpRequest
import com.fathersprophets.backend.modules.auth.models.VerifyResetOtpRequest
import com.fathersprophets.backend.modules.auth.models.VerifyResetOtpResponse
import com.fathersprophets.backend.modules.otp.OtpCreateDto
import com.fathersprophets.backend.modules.otp.OtpDao
import com.fathersprophets.backend.modules.otp.OtpDto
import com.fathersprophets.backend.modules.otp.OtpUpdateDto
import com.fathersprophets.backend.modules.token.TokenCreateDto
import com.fathersprophets.backend.modules.token.TokenDao
import com.fathersprophets.backend.modules.token.TokenUpdateDto
import com.fathersprophets.backend.modules.user.UserCreateDto
import com.fathersprophets.backend.modules.user.UserDao
import com.fathersprophets.backend.modules.user.UserDto
import com.fathersprophets.backend.modules.user.UserUpdateDto
import com.fathersprophets.backend.utils.JwtConfig
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.MailSender
import com.fathersprophets.backend.utils.PasswordUtil
import java.time.Instant
import java.util.UUID

class AuthRepository(
    userDao: UserDao,
    private val tokenDao: TokenDao,
    private val otpDao: OtpDao
) : BaseRepository<UserDto, UserCreateDto, UserUpdateDto, UserDao>(userDao, tokenDao, otpDao), IAuthRepository {

    override fun register(dto: RegisterRequest, lang: String): ApiResponse<AuthResponseResponse> {
        if (dao.getByUsername(dto.username) != null) {
            throw ConflictException(Localization.get("username_exists", lang))
        }
        if (dao.getByEmail(dto.email) != null) {
            throw ConflictException(Localization.get("email_exists", lang))
        }

        val user = create(
            UserCreateDto(
                firstName = dto.firstName,
                lastName = dto.lastName,
                password = PasswordUtil.hashPassword(dto.password),
                username = dto.username,
                email = dto.email,
                role = UserRole.Member,
                isVerified = false
            )
        ) ?: throw BadRequestException(Localization.get("register_failed", lang))

        issueEmailOtp(user.id, user.email)

        val (token, refreshToken, expiresAt) = issueSession(user, dto.fcmToken)

        return ApiResponse(
            success = true,
            message = Localization.get("register_success", lang),
            data = AuthResponseResponse(user = user, token = token, refreshToken = refreshToken, expiresAt = expiresAt)
        )
    }

    override fun login(dto: LoginRequest, lang: String): ApiResponse<AuthResponseResponse> {
        val user = dao.getByUsername(dto.username) ?: throw NotFoundException(Localization.get("user_not_found", lang))
        val passwordHash = dao.getPasswordHashByUsername(dto.username)
            ?: throw NotFoundException(Localization.get("user_not_found", lang))

        if (!PasswordUtil.checkPassword(dto.password, passwordHash)) {
            throw UnauthorizedException(Localization.get("invalid_credentials", lang))
        }

        if (!user.isVerified) {
            issueEmailOtp(user.id, user.email)
            throw ForbiddenException(Localization.get("account_not_verified", lang))
        }

        val (token, refreshToken, expiresAt) = issueSession(user, dto.fcmToken)

        return ApiResponse(
            success = true,
            message = Localization.get("login_success", lang),
            data = AuthResponseResponse(user = user, token = token, refreshToken = refreshToken, expiresAt = expiresAt)
        )
    }

    override fun verifyOtp(userId: Int, request: VerifyOtpRequest, lang: String): ApiResponse<AuthResponseResponse> {
        val otpEntry = otpDao.getByUserAndType(userId, OtpType.Email)
            ?: throw BadRequestException(Localization.get("otp_not_requested", lang))

        assertOtpValid(otpEntry, request.otpCode, lang)

        val user = update(userId, UserUpdateDto(isVerified = true))
            ?: throw NotFoundException(Localization.get("user_not_found", lang))
        otpDao.delete(otpEntry.id)

        val (token, refreshToken, expiresAt) = issueSession(user, request.fcmToken)

        return ApiResponse(
            success = true,
            message = Localization.get("otp_verified_successfully", lang),
            data = AuthResponseResponse(user = user, token = token, refreshToken = refreshToken, expiresAt = expiresAt)
        )
    }

    override fun refreshToken(userId: Int, refreshToken: String, lang: String): ApiResponse<RefreshTokenResponse> {
        if (JwtConfig.verifyRefreshToken(refreshToken) != userId) {
            throw UnauthorizedException(Localization.get("invalid_token", lang))
        }

        val user = getById(userId) ?: throw NotFoundException(Localization.get("user_not_found", lang))
        val existingToken = tokenDao.getByUserId(userId).firstOrNull()
        if (existingToken == null || existingToken.refreshToken != refreshToken) {
            throw UnauthorizedException(Localization.get("invalid_token", lang))
        }

        val newToken = generateAccessToken(user)
        val newRefreshToken = JwtConfig.generateRefreshToken(user.id)
        val expiresAt = accessTokenExpiryMillis()

        tokenDao.update(
            existingToken.id,
            TokenUpdateDto(token = newToken, refreshToken = newRefreshToken, expiresAt = expiresAt)
        )

        return ApiResponse(
            success = true,
            message = Localization.get("token_updated_successfully", lang),
            data = RefreshTokenResponse(token = newToken, refreshToken = newRefreshToken, expiresAt = expiresAt)
        )
    }

    override fun logout(userId: Int, lang: String): ApiResponse<Nothing> {
        val existingToken = tokenDao.getByUserId(userId).firstOrNull()
            ?: throw NotFoundException(Localization.get("token_not_found", lang))
        tokenDao.delete(existingToken.id)
        return ApiResponse(success = true, message = Localization.get("logout_success", lang))
    }

    // Step 1: confirm the username exists and hand back a masked email for the client to display.
    override fun forgotPassword(dto: ForgotPasswordRequest, lang: String): ApiResponse<ForgotPasswordResponse> {
        val user = dao.getByUsername(dto.username) ?: throw NotFoundException(Localization.get("user_not_found", lang))

        if (user.email.isNullOrBlank()) {
            throw BadRequestException(Localization.get("contact_admin_no_email", lang))
        }

        return ApiResponse(
            success = true,
            message = Localization.get("username_found", lang),
            data = ForgotPasswordResponse(maskedEmail = maskEmail(user.email))
        )
    }

    // Step 2: the user types the real email back; if it matches, email them an OTP.
    override fun sendResetOtp(dto: SendResetOtpRequest, lang: String): ApiResponse<SendResetOtpResponse> {
        val user = dao.getByUsername(dto.username) ?: throw NotFoundException(Localization.get("user_not_found", lang))

        if (user.email.isNullOrBlank() || !user.email.equals(dto.email, ignoreCase = true)) {
            throw BadRequestException(Localization.get("email_not_matched", lang))
        }

        val transactionId = UUID.randomUUID().toString()
        issueResetOtp(user, transactionId)

        return ApiResponse(
            success = true,
            message = Localization.get("otp_sent", lang),
            data = SendResetOtpResponse(transactionId = transactionId)
        )
    }

    override fun resendResetOtp(dto: ResendResetOtpRequest, lang: String): ApiResponse<SendResetOtpResponse> {
        val otpEntry = otpDao.getByResetTransactionId(dto.transactionId)
            ?: throw BadRequestException(Localization.get("invalid_transaction_id", lang))
        val user = getById(otpEntry.userId) ?: throw NotFoundException(Localization.get("user_not_found", lang))

        issueResetOtp(user, dto.transactionId)

        return ApiResponse(
            success = true,
            message = Localization.get("otp_sent", lang),
            data = SendResetOtpResponse(transactionId = dto.transactionId)
        )
    }

    // Step 3: verify the OTP and hand back a short-lived token authorizing the password change.
    override fun verifyResetOtp(dto: VerifyResetOtpRequest, lang: String): ApiResponse<VerifyResetOtpResponse> {
        val otpEntry = otpDao.getByResetTransactionId(dto.transactionId)
            ?: throw BadRequestException(Localization.get("invalid_transaction_id", lang))

        assertOtpValid(otpEntry, dto.otpCode, lang)

        val resetVerifyToken = UUID.randomUUID().toString()
        otpDao.update(
            otpEntry.id,
            OtpUpdateDto(
                resetVerifyToken = resetVerifyToken,
                resetVerifyTokenExpiresAt = Instant.now().plusSeconds(RESET_TOKEN_TTL_SECONDS).toString()
            )
        )

        return ApiResponse(
            success = true,
            message = Localization.get("otp_verified_successfully", lang),
            data = VerifyResetOtpResponse(resetVerifyToken = resetVerifyToken)
        )
    }

    // Step 4: the user submits the new password along with that token.
    override fun resetPassword(dto: ResetPasswordRequest, lang: String): ApiResponse<Nothing> {
        val otpEntry = otpDao.getByResetVerifyToken(dto.resetVerifyToken)
            ?: throw UnauthorizedException(Localization.get("invalid_or_expired_token", lang))

        val expiresAt = otpEntry.resetVerifyTokenExpiresAt?.let { Instant.parse(it) }
        if (expiresAt == null || expiresAt.isBefore(Instant.now())) {
            throw UnauthorizedException(Localization.get("invalid_or_expired_token", lang))
        }

        update(otpEntry.userId, UserUpdateDto(password = PasswordUtil.hashPassword(dto.newPassword)))
        // Single-use: drop the OTP row so the same code/token pair can't be replayed.
        otpDao.delete(otpEntry.id)

        return ApiResponse(success = true, message = Localization.get("password_updated_successfully", lang))
    }

    private fun issueResetOtp(user: UserDto, transactionId: String) {
        val email = user.email
        if (email.isNullOrBlank()) return

        val otp = generateOtpCode()
        val otpExpiresAt = Instant.now().plusSeconds(OTP_TTL_SECONDS).toString()
        val existing = otpDao.getByUserAndType(user.id, OtpType.Email)

        if (existing == null) {
            otpDao.create(
                OtpCreateDto(
                    userId = user.id,
                    type = OtpType.Email,
                    otpCode = otp,
                    otpExpiresAt = otpExpiresAt,
                    resetTransactionId = transactionId
                )
            )
        } else {
            otpDao.update(
                existing.id,
                OtpUpdateDto(otpCode = otp, otpExpiresAt = otpExpiresAt, resetTransactionId = transactionId)
            )
        }

        MailSender.sendOtpEmail(email, otp)
    }

    private fun issueEmailOtp(userId: Int, email: String?) {
        if (email.isNullOrBlank()) return

        val otp = generateOtpCode()
        val otpExpiresAt = Instant.now().plusSeconds(OTP_TTL_SECONDS).toString()
        val existing = otpDao.getByUserAndType(userId, OtpType.Email)

        if (existing == null) {
            otpDao.create(OtpCreateDto(userId = userId, type = OtpType.Email, otpCode = otp, otpExpiresAt = otpExpiresAt))
        } else {
            otpDao.update(existing.id, OtpUpdateDto(otpCode = otp, otpExpiresAt = otpExpiresAt))
        }

        MailSender.sendOtpEmail(email, otp)
    }

    private fun assertOtpValid(otpEntry: OtpDto, submittedCode: String, lang: String) {
        if (otpEntry.otpCode == null || otpEntry.otpCode != submittedCode) {
            throw BadRequestException(Localization.get("otp_invalid", lang))
        }

        val expiresAt = otpEntry.otpExpiresAt?.let { Instant.parse(it) }
        if (expiresAt == null || expiresAt.isBefore(Instant.now())) {
            throw BadRequestException(Localization.get("otp_expired", lang))
        }
    }

    private fun issueSession(user: UserDto, fcmToken: String?): Triple<String, String, Long> {
        val token = generateAccessToken(user)
        val refreshToken = JwtConfig.generateRefreshToken(user.id)
        val expiresAt = accessTokenExpiryMillis()

        val existingToken = tokenDao.getByUserId(user.id).firstOrNull()
        if (existingToken == null) {
            tokenDao.create(
                TokenCreateDto(userId = user.id, token = token, refreshToken = refreshToken, expiresAt = expiresAt, fcmToken = fcmToken)
            )
        } else {
            tokenDao.update(
                existingToken.id,
                TokenUpdateDto(token = token, refreshToken = refreshToken, expiresAt = expiresAt, fcmToken = fcmToken)
            )
        }

        return Triple(token, refreshToken, expiresAt)
    }

    private fun generateAccessToken(user: UserDto): String {
        return JwtConfig.generateAccessToken(user.id, user.username, user.role.name, user.isReviewed, user.fullName)
    }

    private fun accessTokenExpiryMillis(): Long = Instant.now().plusSeconds(ACCESS_TOKEN_TTL_SECONDS).toEpochMilli()

    private fun generateOtpCode(): String = (100000..999999).random().toString()

    private fun maskEmail(email: String): String {
        val at = email.indexOf('@')
        if (at <= 1) return email
        return "${email.take(2)}***${email.substring(at)}"
    }

    companion object {
        private const val OTP_TTL_SECONDS = 600L
        private const val RESET_TOKEN_TTL_SECONDS = 600L
        private const val ACCESS_TOKEN_TTL_SECONDS = 3600L
    }
}