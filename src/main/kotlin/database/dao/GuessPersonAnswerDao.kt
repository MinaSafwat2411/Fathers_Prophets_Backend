package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.AnswerStatus
import com.fathersprophets.backend.database.tables.GuessPersonAnswersTable
import com.fathersprophets.backend.models.dto.GuessPersonAnswerDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class GuessPersonAnswerDao {

    private fun resultRowToDto(row: ResultRow) = GuessPersonAnswerDto(
        id = row[GuessPersonAnswersTable.id],
        questionId = row[GuessPersonAnswersTable.questionId],
        userId = row[GuessPersonAnswersTable.userId],
        personId = row[GuessPersonAnswersTable.personId],
        status = row[GuessPersonAnswersTable.status]
    )

    fun findAll() = transaction {
        GuessPersonAnswersTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        GuessPersonAnswersTable.selectAll().where { GuessPersonAnswersTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun findByQuestionId(questionId: Int) = transaction {
        GuessPersonAnswersTable.selectAll().where { GuessPersonAnswersTable.questionId eq questionId }
            .map { resultRowToDto(it) }
    }

    fun findByUserId(userId: Int) = transaction {
        GuessPersonAnswersTable.selectAll().where { GuessPersonAnswersTable.userId eq userId }
            .map { resultRowToDto(it) }
    }

    fun findByQuestionIdAndUserId(questionId: Int, userId: Int) = transaction {
        GuessPersonAnswersTable.selectAll()
            .where { (GuessPersonAnswersTable.questionId eq questionId) and (GuessPersonAnswersTable.userId eq userId) }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun create(dto: GuessPersonAnswerDto) = transaction {
        GuessPersonAnswersTable.insert {
            it[questionId] = dto.questionId
            it[userId] = dto.userId
            it[personId] = dto.personId
            it[status] = dto.status
        } get GuessPersonAnswersTable.id
    }

    fun update(dto: GuessPersonAnswerDto) = transaction {
        GuessPersonAnswersTable.update({ GuessPersonAnswersTable.id eq dto.id }) {
            it[questionId] = dto.questionId
            it[userId] = dto.userId
            it[personId] = dto.personId
            it[status] = dto.status
        } > 0
    }

    fun updateStatus(dto: GuessPersonAnswerDto) = transaction {
        GuessPersonAnswersTable.update({ GuessPersonAnswersTable.id eq dto.id }) {
            it[status] = dto.status
        } > 0
    }

    fun delete(dto: GuessPersonAnswerDto) = transaction {
        GuessPersonAnswersTable.deleteWhere { GuessPersonAnswersTable.id eq dto.id } > 0
    }
}