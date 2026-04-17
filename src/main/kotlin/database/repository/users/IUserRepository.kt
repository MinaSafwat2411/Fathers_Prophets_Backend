package com.fathersprophets.backend.database.repository.users

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.User
import com.fathersprophets.backend.models.request.users.UpdateEmailRequest
import com.fathersprophets.backend.models.request.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.request.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.request.users.UpdateProfileRequest

interface IUserRepository {
    suspend fun getUserById(id: Int): ApiResponse<User?>
    suspend fun addUser(user: User): ApiResponse<Nothing>
    suspend fun updateEmail(id: Int, updateEmailRequest: UpdateEmailRequest): ApiResponse<Nothing>
    suspend fun updatePassword(id: Int, updatePasswordRequest: UpdatePasswordRequest): ApiResponse<Nothing>
    suspend fun updateProfile(id: Int, updateProfileRequest: UpdateProfileRequest): ApiResponse<Nothing>
    suspend fun updatePhone(id: Int, updatePhoneRequest: UpdatePhoneRequest): ApiResponse<Nothing>

    suspend fun updateReview(id: Int): ApiResponse<Nothing>
    suspend fun updateUserByField(user: User): ApiResponse<Nothing>
    suspend fun deleteUser(id: Int): ApiResponse<Nothing>

    suspend fun getUsersByRole(role: String) : ApiResponse<List<User>>

    suspend fun getUnReviewedUsers(): ApiResponse<List<User>>

    suspend fun getAllUsers(): ApiResponse<List<User>>
}