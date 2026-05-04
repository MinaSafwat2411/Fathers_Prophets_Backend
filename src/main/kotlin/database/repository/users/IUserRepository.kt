package com.fathersprophets.backend.database.repository.users

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.users.User
import com.fathersprophets.backend.models.dto.users.UpdateEmailRequest
import com.fathersprophets.backend.models.dto.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.dto.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.dto.users.UpdateProfileRequest
import com.fathersprophets.backend.models.dto.users.UpdateUserRequest
import com.fathersprophets.backend.models.dto.users.UserResponse

interface IUserRepository {
    suspend fun getUserById(id: Int, lang: String): ApiResponse<UserResponse?>
    suspend fun addUser(user: User, lang: String): ApiResponse<Nothing>
    suspend fun updateEmail(id: Int, updateEmailRequest: UpdateEmailRequest, lang: String): ApiResponse<Nothing>
    suspend fun updatePassword(id: Int, updatePasswordRequest: UpdatePasswordRequest, lang: String): ApiResponse<Nothing>
    suspend fun updateProfile(id: Int, updateProfileRequest: UpdateProfileRequest, lang: String): ApiResponse<Nothing>
    suspend fun updatePhone(id: Int, updatePhoneRequest: UpdatePhoneRequest, lang: String): ApiResponse<Nothing>

    suspend fun updateReview(id: Int, lang: String): ApiResponse<Nothing>
    suspend fun updateUserByField(updateUser: UpdateUserRequest, lang: String): ApiResponse<UserResponse>
    suspend fun deleteUser(id: Int, lang: String): ApiResponse<Nothing>

    suspend fun getUsersByRole(role: String, lang: String) : ApiResponse<List<UserResponse>>

    suspend fun getUnReviewedUsers(lang: String): ApiResponse<List<UserResponse>>

    suspend fun getAllUsers(lang: String): ApiResponse<List<UserResponse>>
}