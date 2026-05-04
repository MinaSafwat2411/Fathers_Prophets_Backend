package com.fathersprophets.backend.database.repository.users

import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.users.User
import com.fathersprophets.backend.models.dto.users.UpdateEmailRequest
import com.fathersprophets.backend.models.dto.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.dto.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.dto.users.UpdateProfileRequest
import com.fathersprophets.backend.models.dto.users.UpdateUserRequest
import com.fathersprophets.backend.models.dto.users.UserResponse
import com.fathersprophets.backend.utils.Localization

class UserRepository(
    val userDao: UserDao,
) : IUserRepository {
    override suspend fun getUserById(id: Int, lang: String): ApiResponse<UserResponse?> {
        val user = userDao.findById(id)
        val userResponse = user?.convertToUserResponse()
        val messageKey = if (userResponse != null) "user_found" else "user_not_found"
        return ApiResponse(success = userResponse != null, data = userResponse, message = Localization.get(messageKey, lang))
    }

    override suspend fun addUser(user: User, lang: String): ApiResponse<Nothing> {
        userDao.createUser(user)
        return ApiResponse(success = true, message = Localization.get("user_added_success", lang))
    }

    override suspend fun updateEmail(id: Int,updateEmailRequest: UpdateEmailRequest, lang: String): ApiResponse<Nothing> {
        userDao.updateEmail(id, updateEmailRequest)
        return ApiResponse(success = true, message = Localization.get("email_updated_successfully", lang))
    }

    override suspend fun updatePassword(id: Int, updatePasswordRequest: UpdatePasswordRequest, lang: String): ApiResponse<Nothing> {
        userDao.updatePassword(id, updatePasswordRequest)
        return ApiResponse(success = true, message = Localization.get("password_updated_successfully", lang))
    }

    override suspend fun updateProfile(id: Int,updateProfileRequest: UpdateProfileRequest, lang: String): ApiResponse<Nothing> {
        userDao.updateProfile(id, updateProfileRequest)
        return ApiResponse(success = true, message = Localization.get("profile_updated_successfully", lang))
    }

    override suspend fun updatePhone(id: Int, updatePhoneRequest: UpdatePhoneRequest, lang: String): ApiResponse<Nothing> {
        userDao.updatePhone(id, updatePhoneRequest)
        return ApiResponse(success = true, message = Localization.get("phone_updated_successfully", lang))
    }

    override suspend fun updateReview(id: Int, lang: String): ApiResponse<Nothing> {
        userDao.reviewUser(id)
        return ApiResponse(success = true, message = Localization.get("user_reviewed_successfully", lang))
    }

    override suspend fun updateUserByField(updateUser: UpdateUserRequest, lang: String): ApiResponse<UserResponse> {
        val  user = userDao.updateUserByField(updateUser)
        return ApiResponse(success = true, message = Localization.get("user_updated_successfully", lang), data = user?.convertToUserResponse())
    }

    override suspend fun deleteUser(id: Int, lang: String): ApiResponse<Nothing> {
        userDao.deleteUser(id)
        return ApiResponse(success = true, message = Localization.get("user_deleted_successfully", lang))
    }

    override suspend fun getUsersByRole(role: String, lang: String): ApiResponse<List<UserResponse>> {
        val users = userDao.findByRole(role).map { it.convertToUserResponse() }
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
}
