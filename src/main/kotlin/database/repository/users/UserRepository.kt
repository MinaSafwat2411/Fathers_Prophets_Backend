package com.fathersprophets.backend.database.repository.users

import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.User
import com.fathersprophets.backend.models.request.users.UpdateEmailRequest
import com.fathersprophets.backend.models.request.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.request.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.request.users.UpdateProfileRequest
import com.fathersprophets.backend.utils.Localization

class UserRepository(
    val userDao: UserDao,
    private val lang: String = "en"
) : IUserRepository {
    override suspend fun getUserById(id: Int): ApiResponse<User?> {
        val user = userDao.findById(id)
        return ApiResponse(success = true, data = user, message = Localization.get("user_found", lang))
    }

    override suspend fun addUser(user: User): ApiResponse<Nothing> {
        userDao.createUser(user)
        return ApiResponse(success = true, message = Localization.get("user_added_success", lang))
    }

    override suspend fun updateEmail(id: Int,updateEmailRequest: UpdateEmailRequest): ApiResponse<Nothing> {
        userDao.updateEmail(id, updateEmailRequest)
        return ApiResponse(success = true, message = Localization.get("update_success", lang))
    }

    override suspend fun updatePassword(id: Int, updatePasswordRequest: UpdatePasswordRequest): ApiResponse<Nothing> {
        userDao.updatePassword(id, updatePasswordRequest)
        return ApiResponse(success = true, message = Localization.get("update_success", lang))
    }

    override suspend fun updateProfile(id: Int,updateProfileRequest: UpdateProfileRequest): ApiResponse<Nothing> {
        userDao.updateProfile(id, updateProfileRequest)
        return ApiResponse(success = true, message = Localization.get("update_success", lang))
    }

    override suspend fun updatePhone(id: Int, updatePhoneRequest: UpdatePhoneRequest): ApiResponse<Nothing> {
        userDao.updatePhone(id, updatePhoneRequest)
        return ApiResponse(success = true, message = Localization.get("update_success", lang))
    }

    override suspend fun updateReview(id: Int): ApiResponse<Nothing> {
        userDao.reviewUser(id)
        return ApiResponse(success = true, message = Localization.get("update_success", lang))
    }

    override suspend fun updateUserByField(user: User): ApiResponse<Nothing> {
        userDao.updateUserByField(user)
        return ApiResponse(success = true, message = Localization.get("update_success", lang))
    }

    override suspend fun deleteUser(id: Int): ApiResponse<Nothing> {
        userDao.deleteUser(id)
        return ApiResponse(success = true, message = Localization.get("delete_success", lang))
    }

    override suspend fun getUsersByRole(role: String): ApiResponse<List<User>> {
        val users = userDao.findByRole(role)
        return ApiResponse(success = true, data = users, message = Localization.get("users_found", lang))
    }

    override suspend fun getUnReviewedUsers(): ApiResponse<List<User>> {
        val users = userDao.findUnreviewedUsers()
        return ApiResponse(success = true, data = users, message = Localization.get("users_found", lang))
    }

    override suspend fun getAllUsers(): ApiResponse<List<User>> {
        val users = userDao.findAllUsers()
        return ApiResponse(success = true, data = users, message = Localization.get("users_found", lang))
    }
}
