package com.fathersprophets.backend.database.repository.users

import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.modules.users.UserRole
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.users.*
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.PasswordUtil
import java.time.LocalDate
import java.time.MonthDay
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class UserRepository(
    val userDao: UserDao,
) : IUserRepository {
    override fun getUserById(id: Int, lang: String): ApiResponse<UserResponse> {
        val user =
            userDao.findById(id) ?: throw IllegalArgumentException(Localization.get("user_not_found", lang))
        val userResponse = user.convertToUserResponse()
        val messageKey = "user_found"
        return ApiResponse(
            success = true,
            data = userResponse,
            message = Localization.get(messageKey, lang)
        )
    }

    override fun addUser(addUserRequest: AddUserRequest, lang: String): ApiResponse<UserResponse> {
        val hashPassword = PasswordUtil.hashPassword("123456")
        val user = userDao.createUser(addUserRequest.toUserDto(0, hashPassword))?:
            throw IllegalArgumentException(Localization.get("user_add_failed", lang))

        return ApiResponse(
            success = true,
            data = user.convertToUserResponse(),
            message = Localization.get("user_added_success", lang)
        )
    }

    override fun updateUser(
        id: Int,
        updateUserRequest: UpdateUserRequest,
        lang: String
    ): ApiResponse<UserResponse> {
        val user = userDao.update(updateUserRequest.toUserDto(id))
            ?: throw IllegalArgumentException(Localization.get("user_update_failed", lang))

        return ApiResponse(
            success = true,
            data = user.convertToUserResponse(),
            message = Localization.get("user_updated_successfully", lang)
        )
    }

    override fun updateEmail(
        id: Int,
        updateEmailRequest: UpdateEmailRequest,
        lang: String
    ): ApiResponse<Nothing> {
        val updated = userDao.updateEmail(updateEmailRequest.toUserDto(id))

        if (!updated) throw IllegalArgumentException(Localization.get("email_update_failed", lang))

        return ApiResponse(
            success = true,
            message = Localization.get("email_updated_successfully", lang)
        )
    }

    override fun updatePassword(
        id: Int,
        updatePasswordRequest: UpdatePasswordRequest,
        lang: String
    ): ApiResponse<Nothing> {
        val oldHashPassword = PasswordUtil.hashPassword(updatePasswordRequest.oldPassword ?: "")
        val newHashPassword = PasswordUtil.hashPassword(updatePasswordRequest.newPassword ?: "")
        val user = userDao.findById(id)
            ?: throw IllegalArgumentException(Localization.get("password_update_failed", lang))
        if (user.passwordHash == newHashPassword) {
            throw IllegalArgumentException(Localization.get("new_password_same_as_old", lang))
        }

        if (user.passwordHash != oldHashPassword) {
            throw IllegalArgumentException(Localization.get("old_password_incorrect", lang))
        }

        val updated = userDao.updatePassword(user.copy(passwordHash = newHashPassword))

        if (!updated) throw IllegalArgumentException(Localization.get("password_update_failed", lang))

        return ApiResponse(
            success = true,
            message = Localization.get(
                "password_updated_successfully",
                lang
            )
        )
    }

    override fun updateProfile(
        id: Int,
        updateProfileRequest: UpdateProfileRequest,
        lang: String
    ): ApiResponse<Nothing> {
        val updated = userDao.updateProfile(updateProfileRequest.toUserDto(id))

        if (!updated) throw IllegalArgumentException(Localization.get("profile_update_failed", lang))

        return ApiResponse(
            success = true,
            message = Localization.get("profile_updated_successfully", lang)
        )
    }

    override fun updatePhone(
        id: Int,
        updatePhoneRequest: UpdatePhoneRequest,
        lang: String
    ): ApiResponse<Nothing> {
        val updated = userDao.updatePhone(updatePhoneRequest.toUserDto(id))

        if (!updated) throw IllegalArgumentException(Localization.get("phone_update_failed", lang))

        return ApiResponse(
            success = true,
            message = Localization.get("phone_updated_successfully", lang)
        )
    }

    override fun updateReview(id: Int, lang: String): ApiResponse<Nothing> {
        val updated = userDao.reviewUser(id)

        if (!updated) throw IllegalArgumentException(Localization.get("user_review_failed", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("user_reviewed_successfully", lang)
        )
    }

    override fun deleteUser(id: Int, lang: String): ApiResponse<Nothing> {
        val deleted = userDao.deleteUser(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("user_deletion_failed", lang))

        return ApiResponse(success = true, message = Localization.get("user_deleted_successfully", lang))
    }

    override fun getUsersByRole(role: String, lang: String): ApiResponse<List<UserResponse>> {
        val userRole = getRoleFromString(role)
        val users = userDao.findByRole(userRole).map { it.convertToUserResponse() }
        return ApiResponse(success = true, data = users, message = Localization.get("users_found", lang))
    }

    override fun getUnReviewedUsers(lang: String): ApiResponse<List<UserResponse>> {
        val users = userDao.findUnreviewedUsers().map { it.convertToUserResponse() }
        return ApiResponse(success = true, data = users, message = Localization.get("users_found", lang))
    }

    override fun getAllUsers(lang: String): ApiResponse<List<UserResponse>> {
        val users = userDao.findAllUsers().map { it.convertToUserResponse() }
        return ApiResponse(success = true, data = users, message = Localization.get("users_found", lang))
    }

    override fun getUpcomingBirthdays(lang: String): ApiResponse<List<UpcomingBirthdayResponse>> {
        val today = LocalDate.now()

        val upcoming = userDao.findUsersWithBirthDate()
            .mapNotNull { user ->
                val birthDate = user.birthDate?.let { runCatching { LocalDate.parse(it) }.getOrNull() }
                if (birthDate == null) return@mapNotNull null

                val monthDay = MonthDay.of(birthDate.month, birthDate.dayOfMonth)
                var nextBirthday = monthDay.atYear(today.year)
                if (nextBirthday.isBefore(today)) {
                    nextBirthday = monthDay.atYear(today.year + 1)
                }

                UpcomingBirthdayResponse(
                    id = user.id,
                    name = user.name,
                    username = user.username,
                    profile = user.profile,
                    birthDate = birthDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    nextBirthdayDate = nextBirthday.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    daysUntil = ChronoUnit.DAYS.between(today, nextBirthday)
                )
            }
            .sortedBy { it.daysUntil }

        return ApiResponse(
            success = true,
            data = upcoming,
            message = Localization.get("upcoming_birthdays_retrieved_successfully", lang)
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
