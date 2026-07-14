package com.fathersprophets.backend.services.users

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.UserDto
import com.fathersprophets.backend.models.users.AddUserRequest
import com.fathersprophets.backend.models.users.UpdateEmailRequest
import com.fathersprophets.backend.models.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.users.UpdateProfileRequest
import com.fathersprophets.backend.models.users.UpcomingBirthdayResponse
import com.fathersprophets.backend.models.users.UpdateUserRequest
import com.fathersprophets.backend.models.users.UserResponse

interface IUserService {
    fun getUserById(id: Int?, lang: String): ApiResponse<UserResponse>
    fun addUser(addUserRequest: AddUserRequest, lang: String): ApiResponse<UserResponse>

    fun updateUser(id: Int?, updateUserRequest: UpdateUserRequest, lang: String): ApiResponse<UserResponse>
    fun updateReview(id: Int?, lang: String): ApiResponse<Nothing>
    fun deleteUser(id: Int?, lang: String): ApiResponse<Nothing>
    fun getUsersByRole(role: String, lang: String): ApiResponse<List<UserResponse>>
    fun getUnReviewedUsers(lang: String): ApiResponse<List<UserResponse>>
    fun getAllUsers(lang: String): ApiResponse<List<UserResponse>>
    fun getUpcomingBirthdays(lang: String): ApiResponse<List<UpcomingBirthdayResponse>>
    fun updateEmail(id: Int?, updateEmailRequest: UpdateEmailRequest, lang: String): ApiResponse<Nothing>
    fun updatePassword(id: Int?, updatePasswordRequest: UpdatePasswordRequest, lang: String): ApiResponse<Nothing>
    fun updateProfile(id: Int?, updateProfileRequest: UpdateProfileRequest, lang: String): ApiResponse<Nothing>
    fun updatePhone(id: Int?, updatePhoneRequest: UpdatePhoneRequest, lang: String): ApiResponse<Nothing>
}
