package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.EscapeEgyptQuestionsTable
import com.fathersprophets.backend.models.dto.EscapeEgyptQuestionDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class EscapeEgyptQuestionDao {

    private fun resultRowToDto(row: ResultRow) = EscapeEgyptQuestionDto(
        id = row[EscapeEgyptQuestionsTable.id],
        escapeEgyptId = row[EscapeEgyptQuestionsTable.escapeEgyptId],
        question = row[EscapeEgyptQuestionsTable.question],
        correctAnswer = row[EscapeEgyptQuestionsTable.correctAnswer]
    )

    fun findAll() = transaction {
        EscapeEgyptQuestionsTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        EscapeEgyptQuestionsTable.selectAll().where { EscapeEgyptQuestionsTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun findByEscapeEgyptId(escapeEgyptId: Int) = transaction {
        EscapeEgyptQuestionsTable.selectAll().where { EscapeEgyptQuestionsTable.escapeEgyptId eq escapeEgyptId }
            .map { resultRowToDto(it) }
    }

    fun create(dto: EscapeEgyptQuestionDto) = transaction {
        EscapeEgyptQuestionsTable.insert {
            it[escapeEgyptId] = dto.escapeEgyptId
            it[question] = dto.question
            it[correctAnswer] = dto.correctAnswer
        } get EscapeEgyptQuestionsTable.id
    }

    fun update(dto: EscapeEgyptQuestionDto) = transaction {
        EscapeEgyptQuestionsTable.update({ EscapeEgyptQuestionsTable.id eq dto.id }) {
            it[escapeEgyptId] = dto.escapeEgyptId
            it[question] = dto.question
            it[correctAnswer] = dto.correctAnswer
        } > 0
    }

    fun delete(dto: EscapeEgyptQuestionDto) = transaction {
        EscapeEgyptQuestionsTable.deleteWhere { EscapeEgyptQuestionsTable.id eq dto.id } > 0
    }
}