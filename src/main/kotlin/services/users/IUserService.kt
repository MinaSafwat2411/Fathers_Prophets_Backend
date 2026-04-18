package com.fathersprophets.backend.services.users

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.users.User
import com.fathersprophets.backend.models.request.users.UpdateEmailRequest
import com.fathersprophets.backend.models.request.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.request.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.request.users.UpdateProfileRequest
import com.fathersprophets.backend.models.request.users.UpdateUserRequest
import com.fathersprophets.backend.models.response.users.UserResponse

interface IUserService {
    suspend fun getUserById(id: Int, lang: String): ApiResponse<UserResponse?>
    suspend fun addUser(user: User, lang: String): ApiResponse<Nothing>
    suspend fun updateReview(id: Int, lang: String): ApiResponse<Nothing>
    suspend fun updateUserByField(updateUser: UpdateUserRequest, lang: String): ApiResponse<UserResponse>
    suspend fun deleteUser(id: Int, lang: String): ApiResponse<Nothing>
    suspend fun getUsersByRole(role: String, lang: String): ApiResponse<List<UserResponse>>
    suspend fun getUnReviewedUsers(lang: String): ApiResponse<List<UserResponse>>
    suspend fun getAllUsers(lang: String): ApiResponse<List<UserResponse>>
    suspend fun updateEmail(id: Int, updateEmailRequest: UpdateEmailRequest, lang: String): ApiResponse<Nothing>
    suspend fun updatePassword(id: Int, updatePasswordRequest: UpdatePasswordRequest, lang: String): ApiResponse<Nothing>
    suspend fun updateProfile(id: Int, updateProfileRequest: UpdateProfileRequest, lang: String): ApiResponse<Nothing>
    suspend fun updatePhone(id: Int, updatePhoneRequest: UpdatePhoneRequest, lang: String): ApiResponse<Nothing>
}
