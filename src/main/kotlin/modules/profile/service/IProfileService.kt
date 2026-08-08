package com.fathersprophets.backend.modules.profile.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.database.tables.user.UserDto
import com.fathersprophets.backend.modules.profile.models.ChangeContactOtpResponse
import com.fathersprophets.backend.modules.profile.models.ChangeEmailRequest
import com.fathersprophets.backend.modules.profile.models.ChangePasswordRequest
import com.fathersprophets.backend.modules.profile.models.ChangePhoneRequest
import com.fathersprophets.backend.modules.profile.models.VerifyContactOtpRequest

interface IProfileService {
    fun getProfile(userId: Int, lang: String): ApiResponse<UserDto>
    fun requestEmailChange(userId: Int, dto: ChangeEmailRequest, lang: String): ApiResponse<ChangeContactOtpResponse>
    fun verifyEmailChange(userId: Int, dto: VerifyContactOtpRequest, lang: String): ApiResponse<UserDto>
    fun requestPhoneChange(userId: Int, dto: ChangePhoneRequest, lang: String): ApiResponse<ChangeContactOtpResponse>
    fun verifyPhoneChange(userId: Int, dto: VerifyContactOtpRequest, lang: String): ApiResponse<UserDto>
    fun changeProfileImage(userId: Int, imageUrl: String?, lang: String): ApiResponse<UserDto>
    fun changePassword(userId: Int, dto: ChangePasswordRequest, lang: String): ApiResponse<Nothing>
}