package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.GuessPersonAnswersTable
import com.fathersprophets.backend.models.dto.GuessPersonAnswerDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

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


    fun findByUserIdAndQuestionId(userId: Int, questionId: Int) = transaction {
        GuessPersonAnswersTable.selectAll().where { (GuessPersonAnswersTable.userId eq userId) and (GuessPersonAnswersTable.questionId eq questionId) }
            .map { resultRowToDto(it) }
    }

    fun create(dto: GuessPersonAnswerDto) = transaction {
        GuessPersonAnswersTable.insert {
            it[questionId] = dto.questionId
            it[userId] = dto.userId
            it[personId] = dto.personId
            it[status] = dto.status
        }.let { findById(it[GuessPersonAnswersTable.id]) }
    }

    fun update(dto: GuessPersonAnswerDto) = transaction {
        GuessPersonAnswersTable.update({ GuessPersonAnswersTable.id eq dto.id }) {
            it[questionId] = dto.questionId
            it[userId] = dto.userId
            it[personId] = dto.personId
            it[status] = dto.status
        }.let { findById(dto.id) }
    }

    fun updateStatus(dto: GuessPersonAnswerDto) = transaction {
        GuessPersonAnswersTable.update({ GuessPersonAnswersTable.id eq dto.id }) {
            it[status] = dto.status
        }.let { findById(dto.id) }
    }

    fun delete(guessPersonId: Int) = transaction {
        GuessPersonAnswersTable.deleteWhere { GuessPersonAnswersTable.id eq guessPersonId } > 0
    }
}