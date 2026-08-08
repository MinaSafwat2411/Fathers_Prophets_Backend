package com.fathersprophets.backend.modules.profile.repository

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.base.BaseRepository
import com.fathersprophets.backend.database.enums.OtpType
import com.fathersprophets.backend.database.tables.user.UserCreateDto
import com.fathersprophets.backend.database.tables.user.UserDao
import com.fathersprophets.backend.database.tables.user.UserDto
import com.fathersprophets.backend.database.tables.user.UserUpdateDto
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.ConflictException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.exceptions.UnauthorizedException
import com.fathersprophets.backend.modules.otp.OtpCreateDto
import com.fathersprophets.backend.modules.otp.OtpDao
import com.fathersprophets.backend.modules.otp.OtpDto
import com.fathersprophets.backend.modules.profile.models.ChangeContactOtpResponse
import com.fathersprophets.backend.modules.profile.models.ChangeEmailRequest
import com.fathersprophets.backend.modules.profile.models.ChangePasswordRequest
import com.fathersprophets.backend.modules.profile.models.ChangePhoneRequest
import com.fathersprophets.backend.modules.profile.models.VerifyContactOtpRequest
import com.fathersprophets.backend.utils.DotEnv
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.MailSender
import com.fathersprophets.backend.utils.PasswordUtil
import com.fathersprophets.backend.utils.WhatsAppSender
import java.time.Instant

class ProfileRepository(
    userDao: UserDao,
    private val otpDao: OtpDao
) : BaseRepository<UserDto, UserCreateDto, UserUpdateDto, UserDao>(userDao, otpDao), IProfileRepository {

    override fun getProfile(userId: Int, lang: String): ApiResponse<UserDto> {
        val user = requireUser(userId, lang)
        return ApiResponse(success = true, message = Localization.get("user_found", lang), data = user)
    }

    override fun requestEmailChange(
        userId: Int,
        dto: ChangeEmailRequest,
        lang: String
    ): ApiResponse<ChangeContactOtpResponse> {
        val user = requireUser(userId, lang)
        val newEmail = dto.newEmail.trim()

        if (!EMAIL_PATTERN.matches(newEmail)) {
            throw BadRequestException(Localization.get("invalid_email_format", lang))
        }
        if (newEmail.equals(user.email, ignoreCase = true)) {
            throw BadRequestException(Localization.get("same_email_as_current", lang))
        }
        if (dao.getByEmail(newEmail) != null) {
            throw ConflictException(Localization.get("email_exists", lang))
        }

        val otp = issueOtp(userId, OtpType.Email, newEmail)
        MailSender.sendOtpEmail(newEmail, otp)

        return ApiResponse(
            success = true,
            message = Localization.get("otp_sent", lang),
            data = ChangeContactOtpResponse(maskedTarget = maskEmail(newEmail), expiresInSeconds = OTP_TTL_SECONDS)
        )
    }

    override fun verifyEmailChange(userId: Int, dto: VerifyContactOtpRequest, lang: String): ApiResponse<UserDto> {
        val otpEntry = requireOtp(userId, OtpType.Email, lang)
        val newEmail = otpEntry.pendingValue ?: throw BadRequestException(Localization.get("otp_not_requested", lang))

        assertOtpValid(otpEntry, dto.otpCode, lang)

        // Someone else may have claimed the address between the request and this call.
        dao.getByEmail(newEmail)?.let {
            if (it.id != userId) throw ConflictException(Localization.get("email_exists", lang))
        }

        val updated = update(userId, UserUpdateDto(email = newEmail))
            ?: throw NotFoundException(Localization.get("user_not_found", lang))
        otpDao.delete(otpEntry.id)

        return ApiResponse(
            success = true,
            message = Localization.get("email_updated_successfully", lang),
            data = updated
        )
    }

    override fun requestPhoneChange(
        userId: Int,
        dto: ChangePhoneRequest,
        lang: String
    ): ApiResponse<ChangeContactOtpResponse> {
        val user = requireUser(userId, lang)
        val newPhone = dto.newPhone.filter { it.isDigit() }

        // Mirrors the valid_phone_prefix check constraint on UsersTable, so a bad number is
        // rejected here instead of blowing up as a raw constraint violation on update.
        if (newPhone.length != PHONE_LENGTH || PHONE_PREFIXES.none { newPhone.startsWith(it) }) {
            throw BadRequestException(Localization.get("invalid_phone_format", lang))
        }
        if (newPhone == user.phone) {
            throw BadRequestException(Localization.get("same_phone_as_current", lang))
        }
        if (dao.getByPhone(newPhone) != null) {
            throw ConflictException(Localization.get("phone_exists", lang))
        }

        val otp = issueOtp(userId, OtpType.Phone, newPhone)
        WhatsAppSender.sendOtpWhatsApp(toInternational(newPhone), otp)

        return ApiResponse(
            success = true,
            message = Localization.get("otp_sent_whatsapp", lang),
            data = ChangeContactOtpResponse(maskedTarget = maskPhone(newPhone), expiresInSeconds = OTP_TTL_SECONDS)
        )
    }

    override fun verifyPhoneChange(userId: Int, dto: VerifyContactOtpRequest, lang: String): ApiResponse<UserDto> {
        val otpEntry = requireOtp(userId, OtpType.Phone, lang)
        val newPhone = otpEntry.pendingValue ?: throw BadRequestException(Localization.get("otp_not_requested", lang))

        assertOtpValid(otpEntry, dto.otpCode, lang)

        dao.getByPhone(newPhone)?.let {
            if (it.id != userId) throw ConflictException(Localization.get("phone_exists", lang))
        }

        val updated = update(userId, UserUpdateDto(phone = newPhone))
            ?: throw NotFoundException(Localization.get("user_not_found", lang))
        otpDao.delete(otpEntry.id)

        return ApiResponse(
            success = true,
            message = Localization.get("phone_updated_successfully", lang),
            data = updated
        )
    }

    override fun changeProfileImage(userId: Int, imageUrl: String?, lang: String): ApiResponse<UserDto> {
        if (imageUrl.isNullOrBlank()) {
            throw BadRequestException(Localization.get("image_required", lang))
        }

        val updated = update(userId, UserUpdateDto(profile = imageUrl))
            ?: throw NotFoundException(Localization.get("user_not_found", lang))

        return ApiResponse(
            success = true,
            message = Localization.get("profile_updated_successfully", lang),
            data = updated
        )
    }

    override fun changePassword(userId: Int, dto: ChangePasswordRequest, lang: String): ApiResponse<Nothing> {
        val user = requireUser(userId, lang)
        val currentHash = dao.getPasswordHashByUsername(user.username)
            ?: throw NotFoundException(Localization.get("user_not_found", lang))

        if (!PasswordUtil.checkPassword(dto.oldPassword, currentHash)) {
            throw UnauthorizedException(Localization.get("old_password_incorrect", lang))
        }
        if (PasswordUtil.checkPassword(dto.newPassword, currentHash)) {
            throw BadRequestException(Localization.get("new_password_same_as_old", lang))
        }

        update(userId, UserUpdateDto(password = PasswordUtil.hashPassword(dto.newPassword)))
            ?: throw NotFoundException(Localization.get("user_not_found", lang))

        return ApiResponse(success = true, message = Localization.get("password_updated_successfully", lang))
    }

    private fun requireUser(userId: Int, lang: String): UserDto =
        getById(userId) ?: throw NotFoundException(Localization.get("user_not_found", lang))

    private fun requireOtp(userId: Int, type: OtpType, lang: String): OtpDto =
        otpDao.getByUserAndType(userId, type)
            ?: throw BadRequestException(Localization.get("otp_not_requested", lang))

    // OtpTable has a unique (user_id, type) index and OtpUpdateDto cannot null out columns, so the
    // old row is dropped rather than patched — that also clears any stale password-reset token on it.
    private fun issueOtp(userId: Int, type: OtpType, pendingValue: String): String {
        otpDao.getByUserAndType(userId, type)?.let { otpDao.delete(it.id) }

        val otp = generateOtpCode()
        otpDao.create(
            OtpCreateDto(
                userId = userId,
                type = type,
                otpCode = otp,
                otpExpiresAt = Instant.now().plusSeconds(OTP_TTL_SECONDS).toString(),
                pendingValue = pendingValue
            )
        )
        return otp
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

    private fun generateOtpCode(): String = (100000..999999).random().toString()

    // Numbers are stored in local form (010…); WhatsApp needs them with the country code instead.
    private fun toInternational(phone: String): String = "$COUNTRY_CODE${phone.removePrefix("0")}"

    private fun maskEmail(email: String): String {
        val at = email.indexOf('@')
        if (at <= 1) return email
        return "${email.take(2)}***${email.substring(at)}"
    }

    private fun maskPhone(phone: String): String =
        if (phone.length <= 4) phone else "${phone.take(3)}****${phone.takeLast(2)}"

    companion object {
        private const val OTP_TTL_SECONDS = 600L
        private const val PHONE_LENGTH = 11
        private val PHONE_PREFIXES = listOf("010", "011", "012", "015")
        private val EMAIL_PATTERN = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        private val COUNTRY_CODE = DotEnv.get("OTP_PHONE_COUNTRY_CODE") ?: "20"
    }
}