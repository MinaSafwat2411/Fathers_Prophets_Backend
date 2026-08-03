package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.userprogress.UserProgressQuizTable
import com.fathersprophets.backend.models.dto.UserProgressQuizDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserProgressQuizDao {

    private fun resultRowToDto(row: ResultRow) = UserProgressQuizDto(
        id = row[UserProgressQuizTable.id],
        userId = row[UserProgressQuizTable.userId],
        quizId = row[UserProgressQuizTable.quizId],
        dayId = row[UserProgressQuizTable.dayId],
        score = row[UserProgressQuizTable.score]
    )

    fun findAll() = transaction {
        UserProgressQuizTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        UserProgressQuizTable.selectAll().where { UserProgressQuizTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun findByUserId(userId: Int) = transaction {
        UserProgressQuizTable.selectAll().where { UserProgressQuizTable.userId eq userId }
            .map { resultRowToDto(it) }
    }


    fun create(dto: UserProgressQuizDto) = transaction {
        UserProgressQuizTable.insert {
            it[userId] = dto.userId
            it[quizId] = dto.quizId
            it[dayId] = dto.dayId
            it[score] = dto.score
        }.let { findById(it[UserProgressQuizTable.id]) }
    }

    fun update(dto: UserProgressQuizDto) = transaction {
        UserProgressQuizTable.update({ UserProgressQuizTable.id eq dto.id }) {
            it[userId] = dto.userId
            it[quizId] = dto.quizId
            it[dayId] = dto.dayId
            it[score] = dto.score
        }.let { findById(dto.id) }
    }

    fun delete(id: Int) = transaction {
        UserProgressQuizTable.deleteWhere { UserProgressQuizTable.id eq id } > 0
    }

    fun incrementScore(userId: Int, quizId: Int, dayId: Int, amount: Int) = transaction {
        UserProgressQuizTable.insert {
            it[UserProgressQuizTable.userId] = userId
            it[UserProgressQuizTable.quizId] = quizId
            it[UserProgressQuizTable.dayId] = dayId
            it[score] = amount
        }.let { findById(it[UserProgressQuizTable.id]) }
    }
}