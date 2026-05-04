package com.fathersprophets.backend.services.users

import com.fathersprophets.backend.database.repository.users.IUserRepository
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.users.User
import com.fathersprophets.backend.models.dto.users.UpdateEmailRequest
import com.fathersprophets.backend.models.dto.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.dto.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.dto.users.UpdateProfileRequest
import com.fathersprophets.backend.models.dto.users.UpdateUserRequest
import com.fathersprophets.backend.models.dto.users.UserResponse
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class UserService(
    private val userRepository: IUserRepository
) : IUserService {
    override suspend fun getUserById(id: Int?, lang: String): ApiResponse<UserResponse?> {
        validateRequired(id to "user_id", lang = lang)
        
        val userResponse = userRepository.getUserById(id!!, lang)
        return userResponse
    }

    override suspend fun addUser(user: User, lang: String): ApiResponse<Nothing> {
        validateRequired(
            user.username to "username",
            user.passwordHash to "password",
            user.role to "role",
            lang = lang
        )
        return userRepository.addUser(user, lang)
    }

    override suspend fun updateReview(id: Int, lang: String): ApiResponse<Nothing> {
        validateRequired(id to "user_id", lang = lang)

        val userExists = userRepository.getUserById(id, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.updateReview(id, lang)
    }

    override suspend fun updateUserByField(updateUser: UpdateUserRequest, lang: String): ApiResponse<UserResponse> {
        validateRequired(updateUser.id to "user_id", lang = lang)

        val userExists = userRepository.getUserById(updateUser.id!!, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.updateUserByField(updateUser, lang)
    }

    override suspend fun deleteUser(id: Int, lang: String): ApiResponse<Nothing> {
        validateRequired(id to "user_id", lang = lang)

        val userExists = userRepository.getUserById(id, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.deleteUser(id, lang)
    }

    override suspend fun getUsersByRole(role: String, lang: String): ApiResponse<List<UserResponse>> {
        validateRequired(role to "role", lang = lang)
        return userRepository.getUsersByRole(role, lang)
    }

    override suspend fun getUnReviewedUsers(lang: String): ApiResponse<List<UserResponse>> {
        return userRepository.getUnReviewedUsers(lang)
    }

    override suspend fun getAllUsers(lang: String): ApiResponse<List<UserResponse>> {
        return userRepository.getAllUsers(lang)
    }

    override suspend fun updateEmail(id: Int, updateEmailRequest: UpdateEmailRequest, lang: String): ApiResponse<Nothing> {
        validateRequired(
            id to "user_id",
            updateEmailRequest.email to "email",
            lang = lang
        )

        val userExists = userRepository.getUserById(id, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.updateEmail(id, updateEmailRequest, lang)
    }

    override suspend fun updatePassword(id: Int, updatePasswordRequest: UpdatePasswordRequest, lang: String): ApiResponse<Nothing> {
        validateRequired(
            id to "user_id",
            updatePasswordRequest.oldPassword to "old_password",
            updatePasswordRequest.newPassword to "new_password",
            lang = lang
        )

        val userExists = userRepository.getUserById(id, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.updatePassword(id, updatePasswordRequest, lang)
    }

    override suspend fun updateProfile(id: Int, updateProfileRequest: UpdateProfileRequest, lang: String): ApiResponse<Nothing> {
        validateRequired(
            id to "user_id",
            updateProfileRequest.profile to "profile",
            lang = lang
        )

        val userExists = userRepository.getUserById(id, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.updateProfile(id, updateProfileRequest, lang)
    }

    override suspend fun updatePhone(id: Int, updatePhoneRequest: UpdatePhoneRequest, lang: String): ApiResponse<Nothing> {
        validateRequired(
            id to "user_id",
            updatePhoneRequest.phone to "phone",
            lang = lang
        )

        val userExists = userRepository.getUserById(id, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.updatePhone(id, updatePhoneRequest, lang)
    }
}
