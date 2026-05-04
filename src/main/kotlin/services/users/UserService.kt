package com.fathersprophets.backend.services.users

import com.fathersprophets.backend.database.repository.users.IUserRepository
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.users.User
import com.fathersprophets.backend.models.request.users.UpdateEmailRequest
import com.fathersprophets.backend.models.request.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.request.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.request.users.UpdateProfileRequest
import com.fathersprophets.backend.models.request.users.UpdateUserRequest
import com.fathersprophets.backend.models.dto.users.UserResponse
import com.fathersprophets.backend.utils.Localization

class UserService(
    private val userRepository: IUserRepository
) : IUserService {
    override suspend fun getUserById(id: Int, lang: String): ApiResponse<UserResponse?> {
        val userResponse = userRepository.getUserById(id, lang)
        if (userResponse.data == null) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userResponse
    }

    override suspend fun addUser(user: User, lang: String): ApiResponse<Nothing> {
        if (user.username.isBlank()) {
            throw BadRequestException(Localization.get("username_empty", lang))
        }
        if (user.passwordHash.isBlank()) {
            throw BadRequestException(Localization.get("password_empty", lang))
        }
        if (user.role.isBlank()) {
            throw BadRequestException(Localization.get("role_empty", lang))
        }
        return userRepository.addUser(user, lang)
    }

    override suspend fun updateReview(id: Int, lang: String): ApiResponse<Nothing> {
        val userExists = userRepository.getUserById(id, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.updateReview(id, lang)
    }

    override suspend fun updateUserByField(updateUser: UpdateUserRequest, lang: String): ApiResponse<UserResponse> {
        val userExists = userRepository.getUserById(updateUser.id, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.updateUserByField(updateUser, lang)
    }

    override suspend fun deleteUser(id: Int, lang: String): ApiResponse<Nothing> {
        val userExists = userRepository.getUserById(id, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.deleteUser(id, lang)
    }

    override suspend fun getUsersByRole(role: String, lang: String): ApiResponse<List<UserResponse>> {
        if (role.isBlank()) {
            throw BadRequestException(Localization.get("role_empty", lang))
        }
        return userRepository.getUsersByRole(role, lang)
    }

    override suspend fun getUnReviewedUsers(lang: String): ApiResponse<List<UserResponse>> {
        return userRepository.getUnReviewedUsers(lang)
    }

    override suspend fun getAllUsers(lang: String): ApiResponse<List<UserResponse>> {
        return userRepository.getAllUsers(lang)
    }

    override suspend fun updateEmail(id: Int, updateEmailRequest: UpdateEmailRequest, lang: String): ApiResponse<Nothing> {
        val userExists = userRepository.getUserById(id, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.updateEmail(id, updateEmailRequest, lang)
    }

    override suspend fun updatePassword(id: Int, updatePasswordRequest: UpdatePasswordRequest, lang: String): ApiResponse<Nothing> {
        val userExists = userRepository.getUserById(id, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.updatePassword(id, updatePasswordRequest, lang)
    }

    override suspend fun updateProfile(id: Int, updateProfileRequest: UpdateProfileRequest, lang: String): ApiResponse<Nothing> {
        val userExists = userRepository.getUserById(id, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.updateProfile(id, updateProfileRequest, lang)
    }

    override suspend fun updatePhone(id: Int, updatePhoneRequest: UpdatePhoneRequest, lang: String): ApiResponse<Nothing> {
        val userExists = userRepository.getUserById(id, lang).data != null
        if (!userExists) {
            throw NotFoundException(Localization.get("user_not_found", lang))
        }
        return userRepository.updatePhone(id, updatePhoneRequest, lang)
    }
}
