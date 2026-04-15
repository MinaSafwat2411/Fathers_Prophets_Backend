package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.UsersTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class UserDao {
    private fun resultRowToUser(row: ResultRow) = row

    fun findByUsername(username: String) = transaction {
        UsersTable.selectAll().where { UsersTable.username eq username }
            .singleOrNull()?.let { resultRowToUser(it) }
    }

    fun findById(id: Int) = transaction {
        UsersTable.selectAll().where { UsersTable.id eq id }
            .singleOrNull()?.let { resultRowToUser(it) }
    }

    fun createUser(data: Map<String, Any?>) = transaction {
        UsersTable.insert {
            it[UsersTable.name] = data["name"] as String
            it[UsersTable.username] = data["username"] as String
            it[UsersTable.passwordHash] = data["password_hash"] as String
            it[UsersTable.role] = data["role"] as String
            it[UsersTable.email] = data["email"] as? String
            it[UsersTable.phone] = data["phone"] as? String
            it[UsersTable.address] = data["address"] as? String
            it[UsersTable.birthDate] = data["birth_date"] as? LocalDate
            it[UsersTable.fatherName] = data["father_name"] as? String
            it[UsersTable.isShams] = data["is_shams"] as? Boolean
            it[UsersTable.profile] = data["profile"] as? String
            it[UsersTable.isReviewed] = data["is_reviewed"] as? Boolean
            it[UsersTable.fcmToken] = data["fcm_token"] as? String
            it[UsersTable.classId] = data["class_id"] as? Int
            it[UsersTable.chats] = data["chats"] as? String
            it[UsersTable.memberId] = data["member_id"] as? String
            it[UsersTable.skipMembership] = data["skip_membership"] as? Boolean
            it[UsersTable.comments] = data["comments"] as? String
            it[UsersTable.token] = data["token"] as? String
            it[UsersTable.refreshToken] = data["refresh_token"] as? String
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
}
