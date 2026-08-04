package com.fathersprophets.backend.modules.guesspersonanswer

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class GuessPersonAnswerDao {

    private fun ResultRow.toDto() = GuessPersonAnswerDto(
        id = this[GuessPersonAnswersTable.id],
        questionId = this[GuessPersonAnswersTable.questionId],
        userId = this[GuessPersonAnswersTable.userId],
        answer = this[GuessPersonAnswersTable.answer],
        status = this[GuessPersonAnswersTable.status]
    )

    fun getAll() = transaction {
        GuessPersonAnswersTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        GuessPersonAnswersTable.selectAll()
            .where { GuessPersonAnswersTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByQuestionId(questionId: Int) = transaction {
        GuessPersonAnswersTable.selectAll()
            .where { GuessPersonAnswersTable.questionId eq questionId }
            .map { it.toDto() }
    }

    fun getByUserId(userId: Int) = transaction {
        GuessPersonAnswersTable.selectAll()
            .where { GuessPersonAnswersTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByQuestionAndUser(questionId: Int, userId: Int) = transaction {
        GuessPersonAnswersTable.selectAll()
            .where { (GuessPersonAnswersTable.questionId eq questionId) and (GuessPersonAnswersTable.userId eq userId) }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun create(dto: GuessPersonAnswerCreateDto) = transaction {
        GuessPersonAnswersTable.insert {
            it[questionId] = dto.questionId
            it[userId] = dto.userId
            it[answer] = dto.answer
            it[status] = dto.status
        }.let { getById(it[GuessPersonAnswersTable.id]) }
    }

    fun update(id: Int, dto: GuessPersonAnswerUpdateDto) = transaction {
        GuessPersonAnswersTable.update({ GuessPersonAnswersTable.id eq id }) { updateStatement ->
            dto.questionId?.let { updateStatement[GuessPersonAnswersTable.questionId] = it }
            dto.userId?.let { updateStatement[GuessPersonAnswersTable.userId] = it }
            dto.answer?.let { updateStatement[GuessPersonAnswersTable.answer] = it }
            dto.status?.let { updateStatement[GuessPersonAnswersTable.status] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        GuessPersonAnswersTable.deleteWhere { GuessPersonAnswersTable.id eq id } > 0
    }
}