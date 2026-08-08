package com.fathersprophets.backend.modules.user.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.base.BaseService
import com.fathersprophets.backend.database.tables.user.UserCreateDto
import com.fathersprophets.backend.database.tables.user.UserDto
import com.fathersprophets.backend.database.tables.user.UserUpdateDto
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.ConflictException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.modules.user.repository.UserRepository
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class UserService(
    userRepository: UserRepository
) : BaseService<UserDto, UserCreateDto, UserUpdateDto, UserRepository>(userRepository), IUserService {

    override fun getAll(lang: String): ApiResponse<List<UserDto>> {
        return ApiResponse(success = true, message = Localization.get("users_found", lang), data = repository.getAll())
    }

    override fun getById(id: Int, lang: String): ApiResponse<UserDto> {
        validateRequired(id to "user_id", lang = lang)
        val user = repository.getById(id) ?: throw NotFoundException(Localization.get("user_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("user_found", lang), data = user)
    }

    override fun getByUsername(username: String, lang: String): ApiResponse<UserDto> {
        validateRequired(username to "username", lang = lang)
        val user = repository.getByUsername(username)
            ?: throw NotFoundException(Localization.get("user_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("user_found", lang), data = user)
    }

    override fun getByEmail(email: String, lang: String): ApiResponse<UserDto> {
        validateRequired(email to "email", lang = lang)
        val user = repository.getByEmail(email) ?: throw NotFoundException(Localization.get("user_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("user_found", lang), data = user)
    }

    override fun getByPhone(phone: String, lang: String): ApiResponse<UserDto> {
        validateRequired(phone to "phone", lang = lang)
        val user = repository.getByPhone(phone) ?: throw NotFoundException(Localization.get("user_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("user_found", lang), data = user)
    }

    override fun getByMemberId(memberId: String, lang: String): ApiResponse<UserDto> {
        validateRequired(memberId to "member_id", lang = lang)
        val user = repository.getByMemberId(memberId)
            ?: throw NotFoundException(Localization.get("user_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("user_found", lang), data = user)
    }

    override fun getUsersWithBirthDate(lang: String): ApiResponse<List<UserDto>> {
        return ApiResponse(
            success = true,
            message = Localization.get("users_found", lang),
            data = repository.getUsersWithBirthDate()
        )
    }

    override fun getByFamilyId(familyId: Int, lang: String): ApiResponse<List<UserDto>> {
        validateRequired(familyId to "family_id", lang = lang)
        return ApiResponse(
            success = true,
            message = Localization.get("users_found", lang),
            data = repository.getByFamilyId(familyId)
        )
    }

    override fun getByClassId(classId: Int, lang: String): ApiResponse<List<UserDto>> {
        validateRequired(classId to "class_id", lang = lang)
        return ApiResponse(
            success = true,
            message = Localization.get("users_found", lang),
            data = repository.getByClassId(classId)
        )
    }

    override fun create(dto: UserCreateDto, lang: String): ApiResponse<UserDto> {
        validateRequired(
            dto.firstName to "first_name",
            dto.lastName to "last_name",
            dto.username to "username",
            dto.password to "password",
            dto.role to "role",
            lang = lang
        )
        assertUniqueFields(
            currentId = null,
            username = dto.username,
            email = dto.email,
            phone = dto.phone,
            memberId = dto.memberId,
            lang = lang
        )
        val created = repository.create(dto)
            ?: throw BadRequestException(Localization.get("create_user_failed", lang))
        return ApiResponse(success = true, message = Localization.get("user_added_success", lang), data = created)
    }

    override fun update(id: Int, dto: UserUpdateDto, lang: String): ApiResponse<UserDto> {
        validateRequired(id to "user_id", lang = lang)
        assertUniqueFields(
            currentId = id,
            username = dto.username,
            email = dto.email,
            phone = dto.phone,
            memberId = dto.memberId,
            lang = lang
        )
        val updated = repository.update(id, dto) ?: throw NotFoundException(Localization.get("user_not_found", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("user_updated_successfully", lang),
            data = updated
        )
    }

    override fun delete(id: Int, lang: String): ApiResponse<Nothing> {
        validateRequired(id to "user_id", lang = lang)
        if (!repository.delete(id)) throw NotFoundException(Localization.get("user_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("user_deleted_successfully", lang))
    }

    // These columns are unique indexes in UsersTable; check them up front so a clash reads as a 409
    // instead of surfacing as a raw constraint violation. currentId is null on create.
    private fun assertUniqueFields(
        currentId: Int?,
        username: String?,
        email: String?,
        phone: String?,
        memberId: String?,
        lang: String
    ) {
        fun assertFree(existing: UserDto?, messageKey: String) {
            if (existing != null && existing.id != currentId) {
                throw ConflictException(Localization.get(messageKey, lang))
            }
        }

        username?.let { assertFree(repository.getByUsername(it), "username_exists") }
        email?.let { assertFree(repository.getByEmail(it), "email_exists") }
        phone?.let { assertFree(repository.getByPhone(it), "phone_exists") }
        memberId?.let { assertFree(repository.getByMemberId(it), "member_id_exists") }
    }
}