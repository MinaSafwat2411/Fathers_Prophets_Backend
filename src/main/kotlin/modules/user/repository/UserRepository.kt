package com.fathersprophets.backend.modules.user.repository

import com.fathersprophets.backend.base.BaseRepository
import com.fathersprophets.backend.database.tables.user.UserCreateDto
import com.fathersprophets.backend.database.tables.user.UserDao
import com.fathersprophets.backend.database.tables.user.UserDto
import com.fathersprophets.backend.database.tables.user.UserUpdateDto
import com.fathersprophets.backend.utils.PasswordUtil

class UserRepository(
    userDao: UserDao
) : BaseRepository<UserDto, UserCreateDto, UserUpdateDto, UserDao>(userDao), IUserRepository {

    override fun getByUsername(username: String): UserDto? = dao.getByUsername(username)

    override fun getByEmail(email: String): UserDto? = dao.getByEmail(email)

    override fun getByPhone(phone: String): UserDto? = dao.getByPhone(phone)

    override fun getByMemberId(memberId: String): UserDto? = dao.getByMemberId(memberId)

    override fun getUsersWithBirthDate(): List<UserDto> = dao.getUsersWithBirthDate()

    override fun getByFamilyId(familyId: Int): List<UserDto> = dao.getByFamilyId(familyId)

    override fun getByClassId(classId: Int): List<UserDto> = dao.getByClassId(classId)

    // Passwords never reach the DAO in plain text, whichever path creates or updates the user.
    override fun create(dto: UserCreateDto): UserDto? =
        super.create(dto.copy(password = PasswordUtil.hashPassword(dto.password)))

    override fun update(id: Int, dto: UserUpdateDto): UserDto? =
        super.update(id, dto.password?.let { dto.copy(password = PasswordUtil.hashPassword(it)) } ?: dto)
}