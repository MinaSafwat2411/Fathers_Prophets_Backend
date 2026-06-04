package com.fathersprophets.backend.database.repository.users

import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.database.tables.UserRole
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.UserDto
import com.fathersprophets.backend.models.users.AddUserRequest
import com.fathersprophets.backend.models.users.UpdateEmailRequest
import com.fathersprophets.backend.models.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.users.UpdateProfileRequest
import com.fathersprophets.backend.models.users.UpdateUserRequest
import com.fathersprophets.backend.models.users.UserResponse
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.PasswordUtil

class UserRepository(
    val userDao: UserDao,
) : IUserRepository {
    override suspend fun getUserById(id: Int, lang: String): ApiResponse<UserResponse> {
        val user =
            userDao.findById(idToUser(id)) ?: throw IllegalArgumentException(Localization.get("user_not_found", lang))
        val userResponse = user.convertToUserResponse()
        val messageKey = "user_found"
        return ApiResponse(
            success = true,
            data = userResponse,
            message = Localization.get(messageKey, lang)
        )
    }

    override suspend fun addUser(addUserRequest: AddUserRequest, lang: String): ApiResponse<UserResponse> {
        val hashPassword = PasswordUtil.hashPassword("123456")
        val id = userDao.createUser(addUserRequest.toUserDto(0, hashPassword))

        userDao.updateUserByField(addUserRequest.toUserDto(id, hashPassword))

        val user = addUserRequest.toUserDto(id, hashPassword)

        return ApiResponse(
            success = true,
            data = user.convertToUserResponse(),
            message = Localization.get("user_added_success", lang)
        )
    }

    override suspend fun updateEmail(
        id: Int,
        updateEmailRequest: UpdateEmailRequest,
        lang: String
    ): ApiResponse<Nothing> {
        userDao.updateEmail(updateEmailRequest.toUserDto(id))

        return ApiResponse(
            success = true,
            message = Localization.get("email_updated_successfully", lang)
        )
    }

    override suspend fun updatePassword(
        id: Int,
        updatePasswordRequest: UpdatePasswordRequest,
        lang: String
    ): ApiResponse<Nothing> {
        val oldHashPassword = PasswordUtil.hashPassword(updatePasswordRequest.oldPassword ?: "")
        val newHashPassword = PasswordUtil.hashPassword(updatePasswordRequest.newPassword ?: "")
        val user = userDao.findById(idToUser(id))
            ?: throw IllegalArgumentException(Localization.get("password_update_failed", lang))
        if (user.passwordHash == newHashPassword) {
            throw IllegalArgumentException(Localization.get("new_password_same_as_old", lang))
        }

        if (user.passwordHash != oldHashPassword) {
            throw IllegalArgumentException(Localization.get("old_password_incorrect", lang))
        }

        userDao.updatePassword(user.copy(passwordHash = newHashPassword))
        return ApiResponse(
            success = true,
            message = Localization.get(
                "password_updated_successfully",
                lang
            )
        )
    }

    override suspend fun updateProfile(
        id: Int,
        updateProfileRequest: UpdateProfileRequest,
        lang: String
    ): ApiResponse<Nothing> {
        userDao.updateProfile(updateProfileRequest.toUserDto(id))
        return ApiResponse(
            success = true,
            message = Localization.get("profile_updated_successfully", lang)
        )
    }

    override suspend fun updatePhone(
        id: Int,
        updatePhoneRequest: UpdatePhoneRequest,
        lang: String
    ): ApiResponse<Nothing> {
        userDao.updatePhone(updatePhoneRequest.toUserDto(id))
        return ApiResponse(
            success = true,
            message = Localization.get("phone_updated_successfully", lang)
        )
    }

    override suspend fun updateReview(id: Int, lang: String): ApiResponse<Nothing> {
        userDao.reviewUser(id)
        return ApiResponse(
            success = true,
            message = Localization.get("user_reviewed_successfully", lang)
        )
    }

    override suspend fun updateUserByField(
        id: Int,
        updateUser: UpdateUserRequest,
        lang: String
    ): ApiResponse<UserResponse> {
        val user = userDao.updateUserByField(updateUser.toUserDto(id))
            ?: throw IllegalArgumentException(Localization.get("user_update_failed", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("user_updated_successfully", lang),
            data = user.convertToUserResponse()
        )
    }

    override suspend fun deleteUser(id: Int, lang: String): ApiResponse<Nothing> {
        userDao.deleteUser(idToUser(id))
        return ApiResponse(success = true, message = Localization.get("user_deleted_successfully", lang))
    }

    override suspend fun getUsersByRole(role: String, lang: String): ApiResponse<List<UserResponse>> {
        val userRole = getRoleFromString(role)
        val users = userDao.findByRole(userRole).map { it.convertToUserResponse() }
        return ApiResponse(success = true, data = users, message = Localization.get("users_found", lang))
    }

    override suspend fun getUnReviewedUsers(lang: String): ApiResponse<List<UserResponse>> {
        val users = userDao.findUnreviewedUsers().map { it.convertToUserResponse() }
        return ApiResponse(success = true, data = users, message = Localization.get("users_found", lang))
    }

    override suspend fun getAllUsers(lang: String): ApiResponse<List<UserResponse>> {
        val users = userDao.findAllUsers().map { it.convertToUserResponse() }
        return ApiResponse(success = true, data = users, message = Localization.get("users_found", lang))
    }

    private fun idToUser(id: Int): UserDto {
        return UserDto(
            id = id,
            name = "",
            username = "",
            passwordHash = "",
            role = UserRole.bible,
            isReviewed = false,
            fcmToken = ""
        )
    }
    
    private fun getRoleFromString(role: String): UserRole {
        return try {
            UserRole.valueOf(role.lowercase())
        } catch (e: Exception) {
            UserRole.bible
        }
    }
}
