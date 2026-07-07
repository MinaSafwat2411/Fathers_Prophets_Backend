package com.fathersprophets.backend.services.users

import com.fathersprophets.backend.database.repository.users.IUserRepository
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.users.AddUserRequest
import com.fathersprophets.backend.models.users.UpdateEmailRequest
import com.fathersprophets.backend.models.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.users.UpdateProfileRequest
import com.fathersprophets.backend.models.users.UpcomingBirthdayResponse
import com.fathersprophets.backend.models.users.UpdateUserRequest
import com.fathersprophets.backend.models.users.UserResponse
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class UserService(
    private val userRepository: IUserRepository
) : IUserService {
    override suspend fun getUserById(id: Int?, lang: String): ApiResponse<UserResponse> {
        validateRequired(id to "user_id", lang = lang)
        
        val userResponse = userRepository.getUserById(id?:0, lang)
        return userResponse
    }

    override suspend fun addUser(addUserRequest: AddUserRequest, lang: String): ApiResponse<UserResponse> {
        validateRequired(
            addUserRequest.name to "name",
            addUserRequest.username to "username",
            addUserRequest.password to "password",
            addUserRequest.role to "role",
            addUserRequest.isReviewed to "is_reviewed",
            addUserRequest.phone to "phone",
            addUserRequest.address to "address",
            addUserRequest.birthDate to "birthDate",
            addUserRequest.fatherName to "fatherName",
            addUserRequest.isShams to "isShams",
            addUserRequest.memberId to "memberId",
            lang = lang
        )
        
        return userRepository.addUser(addUserRequest, lang)
    }

    override suspend fun updateReview(id: Int?, lang: String): ApiResponse<Nothing> {
        if(id == null){
            throw IllegalArgumentException(Localization.get("id_required", lang))
        }
        
        return userRepository.updateReview(id, lang)
    }

    override suspend fun updateUserByField(id: Int?, updateUser: UpdateUserRequest, lang: String): ApiResponse<UserResponse> {
        if (id == null) {
             throw IllegalArgumentException(Localization.get("id_required", lang))
        }
        return userRepository.updateUserByField(id, updateUser, lang)
    }

    override suspend fun deleteUser(id: Int?, lang: String): ApiResponse<Nothing> {

        if(id == null){
            throw IllegalArgumentException(Localization.get("id_required", lang))
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

    override suspend fun getUpcomingBirthdays(lang: String): ApiResponse<List<UpcomingBirthdayResponse>> {
        return userRepository.getUpcomingBirthdays(lang)
    }

    override suspend fun updateEmail(id: Int?, updateEmailRequest: UpdateEmailRequest, lang: String): ApiResponse<Nothing> {
        if(id == null){
            throw IllegalArgumentException(Localization.get("id_required", lang))
        }
        
        validateRequired(
            updateEmailRequest.email to "email",
            lang = lang
        )

        return userRepository.updateEmail(id, updateEmailRequest, lang)
    }

    override suspend fun updatePassword(id: Int?, updatePasswordRequest: UpdatePasswordRequest, lang: String): ApiResponse<Nothing> {
        if(id == null){
            throw IllegalArgumentException(Localization.get("id_required", lang))
        }
        
        validateRequired(
            updatePasswordRequest.oldPassword to "old_password",
            updatePasswordRequest.newPassword to "new_password",
            lang = lang
        )

        return userRepository.updatePassword(id, updatePasswordRequest, lang)
    }

    override suspend fun updateProfile(id: Int?, updateProfileRequest: UpdateProfileRequest, lang: String): ApiResponse<Nothing> {
        if(id == null){
            throw IllegalArgumentException(Localization.get("id_required", lang))
        }

        validateRequired(
            updateProfileRequest.profile to "profile",
            lang = lang
        )

        return userRepository.updateProfile(id, updateProfileRequest, lang)
    }

    override suspend fun updatePhone(id: Int?, updatePhoneRequest: UpdatePhoneRequest, lang: String): ApiResponse<Nothing> {
        if(id == null){
            throw IllegalArgumentException(Localization.get("id_required", lang))
        }

        validateRequired(
            updatePhoneRequest.phone to "phone",
            lang = lang
        )

        return userRepository.updatePhone(id, updatePhoneRequest, lang)
    }
}
