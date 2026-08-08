package com.fathersprophets.backend.modules.user.repository

import com.fathersprophets.backend.database.tables.user.UserDto

interface IUserRepository {
    fun getByUsername(username: String): UserDto?
    fun getByEmail(email: String): UserDto?
    fun getByPhone(phone: String): UserDto?
    fun getByMemberId(memberId: String): UserDto?
    fun getUsersWithBirthDate(): List<UserDto>
    fun getByFamilyId(familyId: Int): List<UserDto>
    fun getByClassId(classId: Int): List<UserDto>
}