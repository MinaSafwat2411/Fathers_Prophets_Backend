package com.fathersprophets.backend.modules.escapeegyptanswer

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class EscapeEgyptAnswerDao : CrudDao<EscapeEgyptAnswerDto, EscapeEgyptAnswerCreateDto, EscapeEgyptAnswerUpdateDto> {

    private fun ResultRow.toDto() = EscapeEgyptAnswerDto(
        id = this[EscapeEgyptAnswersTable.id],
        escapeQuestionId = this[EscapeEgyptAnswersTable.escapeQuestionId],
        userId = this[EscapeEgyptAnswersTable.userId],
        answer = this[EscapeEgyptAnswersTable.answer],
        status = this[EscapeEgyptAnswersTable.status]
    )

    override fun getAll() = transaction {
        EscapeEgyptAnswersTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        EscapeEgyptAnswersTable.selectAll()
            .where { EscapeEgyptAnswersTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByQuestionId(escapeQuestionId: Int) = transaction {
        EscapeEgyptAnswersTable.selectAll()
            .where { EscapeEgyptAnswersTable.escapeQuestionId eq escapeQuestionId }
            .map { it.toDto() }
    }

    fun getByUserId(userId: Int) = transaction {
        EscapeEgyptAnswersTable.selectAll()
            .where { EscapeEgyptAnswersTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByQuestionAndUser(escapeQuestionId: Int, userId: Int) = transaction {
        EscapeEgyptAnswersTable.selectAll()
            .where { (EscapeEgyptAnswersTable.escapeQuestionId eq escapeQuestionId) and (EscapeEgyptAnswersTable.userId eq userId) }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: EscapeEgyptAnswerCreateDto) = transaction {
        EscapeEgyptAnswersTable.insert {
            it[escapeQuestionId] = dto.escapeQuestionId
            it[userId] = dto.userId
            it[answer] = dto.answer
            it[status] = dto.status
        }.let { getById(it[EscapeEgyptAnswersTable.id]) }
    }

    override fun update(id: Int, dto: EscapeEgyptAnswerUpdateDto) = transaction {
        EscapeEgyptAnswersTable.update({ EscapeEgyptAnswersTable.id eq id }) { updateStatement ->
            dto.answer?.let { updateStatement[EscapeEgyptAnswersTable.answer] = it }
            dto.status?.let { updateStatement[EscapeEgyptAnswersTable.status] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        EscapeEgyptAnswersTable.deleteWhere { EscapeEgyptAnswersTable.id eq id } > 0
    }
}