package com.fathersprophets.backend.modules.escapeegyptquestion

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class EscapeEgyptQuestionDao : CrudDao<EscapeEgyptQuestionDto, EscapeEgyptQuestionCreateDto, EscapeEgyptQuestionUpdateDto> {

    private fun ResultRow.toDto() = EscapeEgyptQuestionDto(
        id = this[EscapeEgyptQuestionsTable.id],
        escapeEgyptId = this[EscapeEgyptQuestionsTable.escapeEgyptId],
        question = this[EscapeEgyptQuestionsTable.question],
        correctAnswer = this[EscapeEgyptQuestionsTable.correctAnswer]
    )

    override fun getAll() = transaction {
        EscapeEgyptQuestionsTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        EscapeEgyptQuestionsTable.selectAll()
            .where { EscapeEgyptQuestionsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByEscapeEgyptId(escapeEgyptId: Int) = transaction {
        EscapeEgyptQuestionsTable.selectAll()
            .where { EscapeEgyptQuestionsTable.escapeEgyptId eq escapeEgyptId }
            .map { it.toDto() }
    }

    override fun create(dto: EscapeEgyptQuestionCreateDto) = transaction {
        EscapeEgyptQuestionsTable.insert {
            it[escapeEgyptId] = dto.escapeEgyptId
            it[question] = dto.question
            it[correctAnswer] = dto.correctAnswer
        }.let { getById(it[EscapeEgyptQuestionsTable.id]) }
    }

    override fun update(id: Int, dto: EscapeEgyptQuestionUpdateDto) = transaction {
        EscapeEgyptQuestionsTable.update({ EscapeEgyptQuestionsTable.id eq id }) { updateStatement ->
            dto.escapeEgyptId?.let { updateStatement[EscapeEgyptQuestionsTable.escapeEgyptId] = it }
            dto.question?.let { updateStatement[EscapeEgyptQuestionsTable.question] = it }
            dto.correctAnswer?.let { updateStatement[EscapeEgyptQuestionsTable.correctAnswer] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        EscapeEgyptQuestionsTable.deleteWhere { EscapeEgyptQuestionsTable.id eq id } > 0
    }
}