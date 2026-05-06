package com.fathersprophets.backend.services.users

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.UserDto
import com.fathersprophets.backend.models.users.AddUserRequest
import com.fathersprophets.backend.models.users.UpdateEmailRequest
import com.fathersprophets.backend.models.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.users.UpdateProfileRequest
import com.fathersprophets.backend.models.users.UpdateUserRequest
import com.fathersprophets.backend.models.users.UserResponse

interface IUserService {
    suspend fun getUserById(id: Int?, lang: String): ApiResponse<UserResponse>
    suspend fun addUser(addUserRequest: AddUserRequest, lang: String): ApiResponse<UserResponse>
    suspend fun updateReview(id: Int?, lang: String): ApiResponse<Nothing>
    suspend fun updateUserByField(id: Int?, updateUser: UpdateUserRequest, lang: String): ApiResponse<UserResponse>
    suspend fun deleteUser(id: Int?, lang: String): ApiResponse<Nothing>
    suspend fun getUsersByRole(role: String, lang: String): ApiResponse<List<UserResponse>>
    suspend fun getUnReviewedUsers(lang: String): ApiResponse<List<UserResponse>>
    suspend fun getAllUsers(lang: String): ApiResponse<List<UserResponse>>
    suspend fun updateEmail(id: Int?, updateEmailRequest: UpdateEmailRequest, lang: String): ApiResponse<Nothing>
    suspend fun updatePassword(id: Int?, updatePasswordRequest: UpdatePasswordRequest, lang: String): ApiResponse<Nothing>
    suspend fun updateProfile(id: Int?, updateProfileRequest: UpdateProfileRequest, lang: String): ApiResponse<Nothing>
    suspend fun updatePhone(id: Int?, updatePhoneRequest: UpdatePhoneRequest, lang: String): ApiResponse<Nothing>
}
