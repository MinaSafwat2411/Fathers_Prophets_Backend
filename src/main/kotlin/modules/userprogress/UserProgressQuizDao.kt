package com.fathersprophets.backend.modules.userprogress

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserProgressQuizDao {

    private fun ResultRow.toDto() = UserProgressQuizDto(
        id = this[UserProgressQuizTable.id],
        userId = this[UserProgressQuizTable.userId],
        quizId = this[UserProgressQuizTable.quizId],
        dayId = this[UserProgressQuizTable.dayId],
        score = this[UserProgressQuizTable.score]
    )

    fun getAll() = transaction {
        UserProgressQuizTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        UserProgressQuizTable.selectAll()
            .where { UserProgressQuizTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByUserId(userId: Int) = transaction {
        UserProgressQuizTable.selectAll()
            .where { UserProgressQuizTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByQuizId(quizId: Int) = transaction {
        UserProgressQuizTable.selectAll()
            .where { UserProgressQuizTable.quizId eq quizId }
            .map { it.toDto() }
    }

    fun getByUserQuizAndDay(userId: Int, quizId: Int, dayId: Int) = transaction {
        UserProgressQuizTable.selectAll()
            .where {
                (UserProgressQuizTable.userId eq userId) and
                    (UserProgressQuizTable.quizId eq quizId) and
                    (UserProgressQuizTable.dayId eq dayId)
            }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun create(dto: UserProgressQuizCreateDto) = transaction {
        UserProgressQuizTable.insert {
            it[userId] = dto.userId
            it[quizId] = dto.quizId
            it[dayId] = dto.dayId
            it[score] = dto.score
        }.let { getById(it[UserProgressQuizTable.id]) }
    }

    fun update(id: Int, dto: UserProgressQuizUpdateDto) = transaction {
        UserProgressQuizTable.update({ UserProgressQuizTable.id eq id }) { updateStatement ->
            dto.userId?.let { updateStatement[UserProgressQuizTable.userId] = it }
            dto.quizId?.let { updateStatement[UserProgressQuizTable.quizId] = it }
            dto.dayId?.let { updateStatement[UserProgressQuizTable.dayId] = it }
            dto.score?.let { updateStatement[UserProgressQuizTable.score] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        UserProgressQuizTable.deleteWhere { UserProgressQuizTable.id eq id } > 0
    }
}