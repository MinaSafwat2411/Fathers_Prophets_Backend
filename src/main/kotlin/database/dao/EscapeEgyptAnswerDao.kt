package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.escapeegypt.EscapeEgyptAnswersTable
import com.fathersprophets.backend.database.dto.EscapeEgyptAnswerDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll
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

    fun findByUserIdAndEscapeEgyptId(dto: EscapeEgyptAnswerDto) =  transaction {
        EscapeEgyptAnswersTable.selectAll().where {
            EscapeEgyptAnswersTable.userId eq (dto.userId) and EscapeEgyptAnswersTable.escapeEgyptId.eq(
                dto.escapeEgyptId
            )
        }.map { resultRowToDto(it) }
    }

    fun create(dto: EscapeEgyptAnswerDto) = transaction {
        EscapeEgyptAnswersTable.insert {
            it[escapeEgyptId] = dto.escapeEgyptId
            it[escapeQuestionId] = dto.escapeQuestionId
            it[userId] = dto.userId
            it[answer] = dto.answer
            it[status] = dto.status
        }.let { findById(it[EscapeEgyptAnswersTable.id]) }
    }

    fun update(dto: EscapeEgyptAnswerDto) = transaction {
        EscapeEgyptAnswersTable.update({ EscapeEgyptAnswersTable.id eq dto.id }) {
            it[escapeEgyptId] = dto.escapeEgyptId
            it[escapeQuestionId] = dto.escapeQuestionId
            it[userId] = dto.userId
            it[answer] = dto.answer
            it[status] = dto.status
        }.let { findById(dto.id) }
    }

    fun updateStatus(dto: EscapeEgyptAnswerDto) = transaction {
        EscapeEgyptAnswersTable.update({ EscapeEgyptAnswersTable.id eq dto.id }) {
            it[status] = dto.status
        }.let { findById(dto.id) }
    }

    fun delete(escapeEgyptAnswerId: Int) = transaction {
        EscapeEgyptAnswersTable.deleteWhere { EscapeEgyptAnswersTable.id eq escapeEgyptAnswerId } > 0
    }
}