package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.ParentsTable
import com.fathersprophets.backend.database.tables.UsersTable
import com.fathersprophets.backend.models.dto.ParentsDto
import com.fathersprophets.backend.models.dto.UserDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserDao {
    private fun resultRowToUser(row: ResultRow) = UserDto(
        id = row[UsersTable.id],
        name = row[UsersTable.name],
        username = row[UsersTable.username],
        passwordHash = row[UsersTable.passwordHash],
        role = row[UsersTable.role],
        email = row[UsersTable.email],
        phone = row[UsersTable.phone],
        address = row[UsersTable.address],
        birthDate = row[UsersTable.birthDate].toString(),
        fatherName = row[UsersTable.fatherName],
        isShams = row[UsersTable.isShams],
        profile = row[UsersTable.profile],
        isReviewed = row[UsersTable.isReviewed],
        memberId = row[UsersTable.memberId],
        token = row[UsersTable.token],
        refreshToken = row[UsersTable.refreshToken],
        fcmToken = row[UsersTable.fcmToken],
        skipMembership = row[UsersTable.skipMembership],
        parents = row.getOrNull(ParentsTable.id)?.let {
            ParentsDto(
                motherPhone = row[ParentsTable.motherPhone],
                fatherPhone = row[ParentsTable.fatherPhone]
            )
        }
    )

    fun findByUsername(userDto : UserDto) = transaction {
        (UsersTable leftJoin ParentsTable).selectAll().where { UsersTable.username eq userDto.username }
            .singleOrNull()?.let { resultRowToUser(it) }
    }

    fun findById(userDto : UserDto) = transaction {
        (UsersTable leftJoin ParentsTable).selectAll().where { UsersTable.id eq userDto.id }
            .singleOrNull()?.let { resultRowToUser(it) }
    }

    fun createUser(userDto: UserDto) = transaction {
        val id = UsersTable.insert {
            it[name] = userDto.name
            it[username] = userDto.username
            it[passwordHash] = userDto.passwordHash
            it[role] = userDto.role
            it[isReviewed] = userDto.isReviewed
            it[fcmToken] = userDto.fcmToken
        } get UsersTable.id

        ParentsTable.insert {
            it[userId]  = id
        }

        return@transaction id
    }

    fun updateToken(userDto : UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[UsersTable.token] = userDto.token
        }
    }

    fun updateRefreshToken(userDto : UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[UsersTable.refreshToken] = userDto.refreshToken
        }
    }

    fun updateFcmToken(userDto : UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[UsersTable.fcmToken] = userDto.fcmToken
        }
    }

    fun findByRole(role: String) = transaction {
        (UsersTable leftJoin ParentsTable).selectAll().where { UsersTable.role eq role }
            .map { resultRowToUser(it) }
    }

    fun updateUserByField(userDto: UserDto) = transaction {
        ParentsTable.update({ ParentsTable.id eq userDto.id }) {
            it[motherPhone] = userDto.parents?.motherPhone
            it[fatherPhone] = userDto.parents?.fatherPhone
        }
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[address] = userDto.address
            it[birthDate] = userDto.birthDate
            it[fatherName] = userDto.fatherName
            it[isShams] = userDto.isShams
            it[memberId] = userDto.memberId
        }.let { findById(userDto) }
    }

    fun reviewUser(userId: Int) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.isReviewed] = true
        }
    }

    fun updateEmail(userDto: UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[email] = userDto.email
        }
    }

    fun updatePhone(userDto: UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[phone] = userDto.phone
        }
    }

    fun updateProfile(userDto: UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[UsersTable.profile] = userDto.profile
        }
    }

    fun updatePassword(userDto: UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[passwordHash] = userDto.passwordHash
        }
    }

    fun deleteUser(userDto : UserDto) = transaction {
        UsersTable.deleteWhere { UsersTable.id eq userDto.id }
    }

    fun findAllUsers() = transaction {
        (UsersTable leftJoin ParentsTable).selectAll().map { resultRowToUser(it) }
    }

    fun findUnreviewedUsers() = transaction {
        (UsersTable leftJoin ParentsTable).selectAll().where { UsersTable.isReviewed eq false }
            .map { resultRowToUser(it) }
    }
}
