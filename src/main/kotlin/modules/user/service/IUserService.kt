package com.fathersprophets.backend.modules.user.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.database.tables.user.UserCreateDto
import com.fathersprophets.backend.database.tables.user.UserDto
import com.fathersprophets.backend.database.tables.user.UserUpdateDto

interface IUserService {
    fun getAll(lang: String): ApiResponse<List<UserDto>>
    fun getById(id: Int, lang: String): ApiResponse<UserDto>
    fun getByUsername(username: String, lang: String): ApiResponse<UserDto>
    fun getByEmail(email: String, lang: String): ApiResponse<UserDto>
    fun getByPhone(phone: String, lang: String): ApiResponse<UserDto>
    fun getByMemberId(memberId: String, lang: String): ApiResponse<UserDto>
    fun getUsersWithBirthDate(lang: String): ApiResponse<List<UserDto>>
    fun getByFamilyId(familyId: Int, lang: String): ApiResponse<List<UserDto>>
    fun getByClassId(classId: Int, lang: String): ApiResponse<List<UserDto>>
    fun create(dto: UserCreateDto, lang: String): ApiResponse<UserDto>
    fun update(id: Int, dto: UserUpdateDto, lang: String): ApiResponse<UserDto>
    fun delete(id: Int, lang: String): ApiResponse<Nothing>
}