package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.enums.UserRole
import com.fathersprophets.backend.database.tables.UsersTable
import com.fathersprophets.backend.models.dto.UserDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class UserDao {
    private fun resultRowToUser(row: ResultRow) = UserDto(
        id = row[UsersTable.id],
        name = row[UsersTable.name],
        username = row[UsersTable.username],
        email = row[UsersTable.email],
        phone = row[UsersTable.phone],
        address = row[UsersTable.address],
        birthDate = row[UsersTable.birthDate],
        fatherConfession = row[UsersTable.fatherConfession],
        fatherPhone = row[UsersTable.fatherPhone],
        motherPhone = row[UsersTable.motherPhone],
        isShams = row[UsersTable.isShams],
        profile = row[UsersTable.profile],
        isReviewed = row[UsersTable.isReviewed],
        role = row[UsersTable.role],
        memberId = row[UsersTable.memberId],
        familyId = row[UsersTable.familyId],
        classId = row[UsersTable.classId],
        score = row[UsersTable.score],
        password = row[UsersTable.password]
    )

    fun findUserById(userId: Int) = transaction {
        UsersTable.selectAll().where { UsersTable.id eq userId }.map(::resultRowToUser).singleOrNull()

    }

    fun findUserByUserName(username: String) = transaction {
        UsersTable.selectAll().where { UsersTable.username eq username }.map(::resultRowToUser).singleOrNull()
    }

    fun findAllUsers(): List<UserDto> = transaction {
        UsersTable.selectAll().map(::resultRowToUser)
    }

    fun findUsersByFamilyId(familyId: Int) = transaction {
        UsersTable.selectAll().where { UsersTable.familyId eq familyId }.map(::resultRowToUser)
    }

    fun findUsersByClassId(classId: Int) = transaction {
        UsersTable.selectAll().where { UsersTable.classId eq classId }.map(::resultRowToUser)
    }

    fun findUsersByRole(role: UserRole) = transaction {
        UsersTable.selectAll().where { UsersTable.role eq role }.map(::resultRowToUser)
    }

    fun findUnReviewedUser() = transaction {
        UsersTable.selectAll().where { UsersTable.isReviewed eq false }.map(::resultRowToUser).singleOrNull()
    }


    fun findUserByIsShmas(isShams: Boolean) = transaction {
        UsersTable.selectAll().where { UsersTable.isShams eq isShams }.map(::resultRowToUser)
    }

    fun findUpComingBirthdays() = transaction {
        UsersTable.selectAll().where { UsersTable.birthDate.isNotNull() }.map(::resultRowToUser)
    }

    fun createUser(user: UserDto) = transaction {
        UsersTable.insert {
            it[name] = user.name
            it[username] = user.username
            it[email] = user.email
            it[phone] = user.phone
            it[address] = user.address
            it[birthDate] = user.birthDate
            it[fatherConfession] = user.fatherConfession
            it[fatherPhone] = user.fatherPhone
            it[motherPhone] = user.motherPhone
            it[isShams] = user.isShams
            it[profile] = user.profile
            it[isReviewed] = user.isReviewed
            it[role] = user.role
            it[memberId] = user.memberId
            it[familyId] = user.familyId
            it[classId] = user.classId
            it[score] = user.score
            it[password] = user.password
        }.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    fun incrementScore(score: Int, userId: Int) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            with(SqlExpressionBuilder) {
                it.update(UsersTable.score, UsersTable.score + score)
            }
        }.let { findUserById(userId) }
    }

    fun joinUserToClass(classId: Int, userId: Int) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.classId] = classId
        }.let { findUserById(userId) }
    }


    fun joinUserToFamily(familyId: Int, userId: Int) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.familyId] = familyId
        }.let { findUserById(userId) }
    }

    fun updateProfile(profile: String, userId: Int) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.profile] = profile
        }.let { findUserById(userId) }
    }

    fun updatePhone(phone: String, userId: Int) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.phone] = phone
        }.let { findUserById(userId) }
    }

    fun updateEmail(email: String, userId: Int) = transaction {
        UsersTable.update({ UsersTable.id eq userId }) {
            it[UsersTable.email] = email
        }.let { findUserById(userId) }
    }

    fun reviewUser(userDto: UserDto) = transaction {
        UsersTable.update({ UsersTable.id eq userDto.id }) {
            it[UsersTable.isReviewed] = true
            it[UsersTable.role] = userDto.role
            it[UsersTable.familyId] = userDto.familyId
            it[UsersTable.classId] = userDto.classId
            it[UsersTable.memberId] = userDto.memberId
            it[UsersTable.isShams] = userDto.isShams
            it[UsersTable.motherPhone] = userDto.motherPhone
            it[UsersTable.fatherPhone] = userDto.fatherPhone
            it[UsersTable.fatherConfession] = userDto.fatherConfession
            it[UsersTable.birthDate] = userDto.birthDate
            it[UsersTable.address] = userDto.address
        }
    }.let { findUserById(userDto.id) }


}