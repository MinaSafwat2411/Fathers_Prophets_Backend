package com.fathersprophets.backend.database.dao.users

import com.fathersprophets.backend.database.tables.users.ParentsTable
import com.fathersprophets.backend.database.tables.users.UserRole
import com.fathersprophets.backend.database.tables.users.UsersTable
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

    fun findByUsername(username: String) = transaction {
        (UsersTable leftJoin ParentsTable).selectAll().where { UsersTable.username eq username }
            .singleOrNull()?.let { resultRowToUser(it) }
    }

    fun findById(userId: Int) = transaction {
        (UsersTable leftJoin ParentsTable).selectAll().where { UsersTable.id eq userId }
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
        findById(id)
    }

    fun updateToken(userDto : UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[UsersTable.token] = userDto.token
        } > 0
    }

    fun updateRefreshToken(userDto : UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[UsersTable.refreshToken] = userDto.refreshToken
        } > 0
    }

    fun updateFcmToken(userDto : UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[UsersTable.fcmToken] = userDto.fcmToken
        } > 0
    }

    fun findByRole(role: UserRole) = transaction {
        (UsersTable leftJoin ParentsTable).selectAll().where { UsersTable.role eq role }
            .map { resultRowToUser(it) }
    }

    fun update(userDto: UserDto) = transaction {
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
        }.let { findById(userDto.id) }
    }

    fun reviewUser(userId: Int) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.isReviewed] = true
        } > 0
    }

    fun updateEmail(userDto: UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[email] = userDto.email
        } > 0
    }

    fun updatePhone(userDto: UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[phone] = userDto.phone
        } > 0
    }

    fun updateProfile(userDto: UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[UsersTable.profile] = userDto.profile
        } > 0
    }

    fun updatePassword(userDto: UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[passwordHash] = userDto.passwordHash
        } > 0
    }

    fun deleteUser(userId: Int) = transaction {
        UsersTable.deleteWhere { UsersTable.id eq userId } > 0
    }

    fun findAllUsers() = transaction {
        (UsersTable leftJoin ParentsTable).selectAll().map { resultRowToUser(it) }
    }

    fun findUnreviewedUsers() = transaction {
        (UsersTable leftJoin ParentsTable).selectAll().where { UsersTable.isReviewed eq false }
            .map { resultRowToUser(it) }
    }

    fun findUsersWithBirthDate() = transaction {
        (UsersTable leftJoin ParentsTable).selectAll().where {
            UsersTable.birthDate.isNotNull()
            UsersTable.isReviewed.eq(true)
        }.map { resultRowToUser(it) }
    }

    fun findAllFcmTokens() = transaction {
        UsersTable.selectAll().where { UsersTable.fcmToken.isNotNull() }
            .mapNotNull { it[UsersTable.fcmToken] }
    }

    fun findFcmTokenById(id: Int) = transaction {
        UsersTable.selectAll().where { UsersTable.id eq id }
            .singleOrNull()?.get(UsersTable.fcmToken)
    }
}
