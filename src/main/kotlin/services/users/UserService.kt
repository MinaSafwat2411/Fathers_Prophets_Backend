package com.fathersprophets.backend.services.users

import com.fathersprophets.backend.database.repository.users.IUserRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.users.*
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class UserService(
    private val userRepository: IUserRepository
) : IUserService {
    override fun getUserById(id: Int?, lang: String): ApiResponse<UserResponse> {
        validateRequired(id to "user_id", lang = lang)
        
        val userResponse = userRepository.getUserById(id?:0, lang)
        return userResponse
    }

    override fun addUser(addUserRequest: AddUserRequest, lang: String): ApiResponse<UserResponse> {
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

    override fun updateUser(
        id: Int?,
        updateUserRequest: UpdateUserRequest,
        lang: String
    ): ApiResponse<UserResponse> {
        if(id == null){
            throw IllegalArgumentException(Localization.get("id_required", lang))
        }

        validateRequired(
            updateUserRequest.address to "address",
            updateUserRequest.birthDate to "birthDate",
            updateUserRequest.fatherName to "fatherName",
            updateUserRequest.isShams to "isShams",
            updateUserRequest.memberId to "memberId",
            lang = lang
        )

        return userRepository.updateUser(id, updateUserRequest, lang)
    }

    override fun updateReview(id: Int?, lang: String): ApiResponse<Nothing> {
        if(id == null){
            throw IllegalArgumentException(Localization.get("id_required", lang))
        }
        
        return userRepository.updateReview(id, lang)
    }


    override fun deleteUser(id: Int?, lang: String): ApiResponse<Nothing> {

        if(id == null){
            throw IllegalArgumentException(Localization.get("id_required", lang))
        }

        return userRepository.deleteUser(id, lang)
    }

    override fun getUsersByRole(role: String, lang: String): ApiResponse<List<UserResponse>> {
        validateRequired(role to "role", lang = lang)
        return userRepository.getUsersByRole(role, lang)
    }

    override fun getUnReviewedUsers(lang: String): ApiResponse<List<UserResponse>> {
        return userRepository.getUnReviewedUsers(lang)
    }

    override fun getAllUsers(lang: String): ApiResponse<List<UserResponse>> {
        return userRepository.getAllUsers(lang)
    }

    override fun getUpcomingBirthdays(lang: String): ApiResponse<List<UpcomingBirthdayResponse>> {
        return userRepository.getUpcomingBirthdays(lang)
    }

    override fun updateEmail(id: Int?, updateEmailRequest: UpdateEmailRequest, lang: String): ApiResponse<Nothing> {
        if(id == null){
            throw IllegalArgumentException(Localization.get("id_required", lang))
        }
        
        validateRequired(
            updateEmailRequest.email to "email",
            lang = lang
        )

        return userRepository.updateEmail(id, updateEmailRequest, lang)
    }

    override fun updatePassword(id: Int?, updatePasswordRequest: UpdatePasswordRequest, lang: String): ApiResponse<Nothing> {
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

    override fun updateProfile(id: Int?, updateProfileRequest: UpdateProfileRequest, lang: String): ApiResponse<Nothing> {
        if(id == null){
            throw IllegalArgumentException(Localization.get("id_required", lang))
        }

        validateRequired(
            updateProfileRequest.profile to "profile",
            lang = lang
        )

        return userRepository.updateProfile(id, updateProfileRequest, lang)
    }

    override fun updatePhone(id: Int?, updatePhoneRequest: UpdatePhoneRequest, lang: String): ApiResponse<Nothing> {
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
