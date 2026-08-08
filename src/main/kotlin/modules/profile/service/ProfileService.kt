package com.fathersprophets.backend.modules.profile.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.base.BaseService
import com.fathersprophets.backend.database.tables.user.UserCreateDto
import com.fathersprophets.backend.database.tables.user.UserDto
import com.fathersprophets.backend.database.tables.user.UserUpdateDto
import com.fathersprophets.backend.modules.profile.models.ChangeContactOtpResponse
import com.fathersprophets.backend.modules.profile.models.ChangeEmailRequest
import com.fathersprophets.backend.modules.profile.models.ChangePasswordRequest
import com.fathersprophets.backend.modules.profile.models.ChangePhoneRequest
import com.fathersprophets.backend.modules.profile.models.VerifyContactOtpRequest
import com.fathersprophets.backend.modules.profile.repository.ProfileRepository
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class ProfileService(
    profileRepository: ProfileRepository
) : BaseService<UserDto, UserCreateDto, UserUpdateDto, ProfileRepository>(profileRepository), IProfileService {

    override fun getProfile(userId: Int, lang: String): ApiResponse<UserDto> {
        validateRequired(userId to "user_id", lang = lang)
        return repository.getProfile(userId, lang)
    }

    override fun requestEmailChange(
        userId: Int,
        dto: ChangeEmailRequest,
        lang: String
    ): ApiResponse<ChangeContactOtpResponse> {
        validateRequired(
            userId to "user_id",
            dto.newEmail to "email",
            lang = lang
        )
        return repository.requestEmailChange(userId, dto, lang)
    }

    override fun verifyEmailChange(userId: Int, dto: VerifyContactOtpRequest, lang: String): ApiResponse<UserDto> {
        validateRequired(
            userId to "user_id",
            dto.otpCode to "otp_code",
            lang = lang
        )
        return repository.verifyEmailChange(userId, dto, lang)
    }

    override fun requestPhoneChange(
        userId: Int,
        dto: ChangePhoneRequest,
        lang: String
    ): ApiResponse<ChangeContactOtpResponse> {
        validateRequired(
            userId to "user_id",
            dto.newPhone to "phone",
            lang = lang
        )
        return repository.requestPhoneChange(userId, dto, lang)
    }

    override fun verifyPhoneChange(userId: Int, dto: VerifyContactOtpRequest, lang: String): ApiResponse<UserDto> {
        validateRequired(
            userId to "user_id",
            dto.otpCode to "otp_code",
            lang = lang
        )
        return repository.verifyPhoneChange(userId, dto, lang)
    }

    override fun changeProfileImage(userId: Int, imageUrl: String?, lang: String): ApiResponse<UserDto> {
        validateRequired(userId to "user_id", lang = lang)
        return repository.changeProfileImage(userId, imageUrl, lang)
    }

    override fun changePassword(userId: Int, dto: ChangePasswordRequest, lang: String): ApiResponse<Nothing> {
        validateRequired(
            userId to "user_id",
            dto.oldPassword to "old_password",
            dto.newPassword to "new_password",
            lang = lang
        )
        return repository.changePassword(userId, dto, lang)
    }
}