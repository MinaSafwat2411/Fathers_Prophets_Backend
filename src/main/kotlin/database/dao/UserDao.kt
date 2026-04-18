package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.UsersTable
import com.fathersprophets.backend.models.dto.users.User
import com.fathersprophets.backend.models.request.users.UpdateEmailRequest
import com.fathersprophets.backend.models.request.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.request.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.request.users.UpdateProfileRequest
import com.fathersprophets.backend.models.request.users.UpdateUserRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserDao {
    private fun resultRowToUser(row: ResultRow) = User(
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
        skipMembership = row[UsersTable.skipMembership]
    )

    fun findByUsername(username: String) = transaction {
        UsersTable.selectAll().where { UsersTable.username eq username }
            .singleOrNull()?.let { resultRowToUser(it) }
    }

    fun findById(id: Int) = transaction {
        UsersTable.selectAll().where { UsersTable.id eq id }
            .singleOrNull()?.let { resultRowToUser(it) }
    }

    fun createUser(user: User) = transaction {
        UsersTable.insert {
            it[name] = user.name
            it[username] = user.username
            it[passwordHash] = user.passwordHash
            it[role] = user.role
            it[isReviewed] = user.isReviewed
        } get UsersTable.id
    }

    fun updateToken(userId: Int, token: String) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.token] = token
        }
    }

    fun updateRefreshToken(userId: Int, refreshToken: String) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.refreshToken] = refreshToken
        }
    }

    fun updateFcmToken(userId: Int, fcm: String) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.fcmToken] = fcm
        }
    }

    fun findByRole(role: String) = transaction {
        UsersTable.selectAll().where { UsersTable.role eq role }
            .map { resultRowToUser(it) }
    }

    fun updateUserByField(updateUser: UpdateUserRequest) = transaction {
        UsersTable.update({ UsersTable.id eq updateUser.id }) {
            it[address] = updateUser.address
            it[birthDate] = updateUser.birthDate
            it[fatherName] = updateUser.fatherName
            it[isShams] = updateUser.isShams
        }.let { findById(updateUser.id) }
    }

    fun reviewUser(userId: Int) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.isReviewed] = true
        }
    }

    fun updateEmail(userId: Int, updateEmailRequest: UpdateEmailRequest) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.email] = updateEmailRequest.email
        }
    }

    fun updatePhone(userId: Int, updatePhoneRequest: UpdatePhoneRequest) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.phone] = updatePhoneRequest.phone
        }
    }

    fun updateProfile(userId: Int, updateProfileRequest: UpdateProfileRequest) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.profile] = updateProfileRequest.profile
        }
    }

    fun updatePassword(userId: Int, updatePasswordRequest: UpdatePasswordRequest) = transaction {
        val isOldRight = UsersTable.selectAll()
            .where { (UsersTable.id eq userId) and (UsersTable.passwordHash eq updatePasswordRequest.oldPassword) }
            .count() > 0

        if (isOldRight) {
            UsersTable.update({ UsersTable.id eq userId }) {
                it[passwordHash] = updatePasswordRequest.newPassword
            }
        }
    }

    fun deleteUser(userId: Int) = transaction {
        UsersTable.deleteWhere { UsersTable.id eq userId }
    }

    fun findAllUsers() = transaction {
        UsersTable.selectAll().map { resultRowToUser(it) }
    }

    fun findUnreviewedUsers() = transaction {
        UsersTable.selectAll().where { UsersTable.isReviewed eq false }
            .map { resultRowToUser(it) }
    }
}
