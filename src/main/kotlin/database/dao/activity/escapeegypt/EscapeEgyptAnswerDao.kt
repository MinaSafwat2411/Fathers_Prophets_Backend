package com.fathersprophets.backend.database.dao.activity.escapeegypt

import com.fathersprophets.backend.database.tables.EscapeEgyptAnswersTable
import com.fathersprophets.backend.models.dto.EscapeEgyptAnswerDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class EscapeEgyptAnswerDao {

    private fun resultRowToDto(row: ResultRow) = EscapeEgyptAnswerDto(
        id = row[EscapeEgyptAnswersTable.id],
        escapeEgyptId = row[EscapeEgyptAnswersTable.escapeEgyptId],
        escapeQuestionId = row[EscapeEgyptAnswersTable.escapeQuestionId],
        userId = row[EscapeEgyptAnswersTable.userId],
        answer = row[EscapeEgyptAnswersTable.answer],
        status = row[EscapeEgyptAnswersTable.status]
    )

    fun findAll() = transaction {
        EscapeEgyptAnswersTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        EscapeEgyptAnswersTable.selectAll().where { EscapeEgyptAnswersTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun findByEscapeEgyptId(escapeEgyptId: Int) = transaction {
        EscapeEgyptAnswersTable.selectAll().where { EscapeEgyptAnswersTable.escapeEgyptId eq escapeEgyptId }
            .map { resultRowToDto(it) }
    }

    fun findByQuestionId(questionId: Int) = transaction {
        EscapeEgyptAnswersTable.selectAll().where { EscapeEgyptAnswersTable.escapeQuestionId eq questionId }
            .map { resultRowToDto(it) }
    }

    fun findByUserId(userId: Int) = transaction {
        EscapeEgyptAnswersTable.selectAll().where { EscapeEgyptAnswersTable.userId eq userId }
            .map { resultRowToDto(it) }
    }

    fun create(dto: EscapeEgyptAnswerDto) = transaction {
        EscapeEgyptAnswersTable.insert {
            it[escapeEgyptId] = dto.escapeEgyptId
            it[escapeQuestionId] = dto.escapeQuestionId
            it[userId] = dto.userId
            it[answer] = dto.answer
            it[status] = dto.status
        } get EscapeEgyptAnswersTable.id
    }

    fun update(dto: EscapeEgyptAnswerDto) = transaction {
        EscapeEgyptAnswersTable.update({ EscapeEgyptAnswersTable.id eq dto.id }) {
            it[escapeEgyptId] = dto.escapeEgyptId
            it[escapeQuestionId] = dto.escapeQuestionId
            it[userId] = dto.userId
            it[answer] = dto.answer
            it[status] = dto.status
        } > 0
    }

    fun updateStatus(dto: EscapeEgyptAnswerDto) = transaction {
        EscapeEgyptAnswersTable.update({ EscapeEgyptAnswersTable.id eq dto.id }) {
            it[status] = dto.status
        } > 0
    }

    fun delete(escapeEgyptAnswerId: Int) = transaction {
        EscapeEgyptAnswersTable.deleteWhere { EscapeEgyptAnswersTable.id eq escapeEgyptAnswerId } > 0
    }
}