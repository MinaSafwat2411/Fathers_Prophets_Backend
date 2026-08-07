package com.fathersprophets.backend.modules.personmcqanswer

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonMcqAnswerDao : CrudDao<PersonMcqAnswerDto, PersonMcqAnswerCreateDto, PersonMcqAnswerUpdateDto> {

    private fun ResultRow.toDto() = PersonMcqAnswerDto(
        id = this[PersonsMcqAnswersTable.id],
        answer = this[PersonsMcqAnswersTable.answer],
        questionId = this[PersonsMcqAnswersTable.questionId],
        userId = this[PersonsMcqAnswersTable.userId],
        status = this[PersonsMcqAnswersTable.status]
    )

    override fun getAll() = transaction {
        PersonsMcqAnswersTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        PersonsMcqAnswersTable.selectAll()
            .where { PersonsMcqAnswersTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByQuestionId(questionId: Int) = transaction {
        PersonsMcqAnswersTable.selectAll()
            .where { PersonsMcqAnswersTable.questionId eq questionId }
            .map { it.toDto() }
    }

    fun getByUserId(userId: Int) = transaction {
        PersonsMcqAnswersTable.selectAll()
            .where { PersonsMcqAnswersTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByQuestionAndUser(questionId: Int, userId: Int) = transaction {
        PersonsMcqAnswersTable.selectAll()
            .where { (PersonsMcqAnswersTable.questionId eq questionId) and (PersonsMcqAnswersTable.userId eq userId) }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: PersonMcqAnswerCreateDto) = transaction {
        PersonsMcqAnswersTable.insert {
            it[answer] = dto.answer
            it[questionId] = dto.questionId
            it[userId] = dto.userId
            it[status] = dto.status
        }.let { getById(it[PersonsMcqAnswersTable.id]) }
    }

    override fun update(id: Int, dto: PersonMcqAnswerUpdateDto) = transaction {
        PersonsMcqAnswersTable.update({ PersonsMcqAnswersTable.id eq id }) { updateStatement ->
            dto.answer?.let { updateStatement[PersonsMcqAnswersTable.answer] = it }
            dto.questionId?.let { updateStatement[PersonsMcqAnswersTable.questionId] = it }
            dto.userId?.let { updateStatement[PersonsMcqAnswersTable.userId] = it }
            dto.status?.let { updateStatement[PersonsMcqAnswersTable.status] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        PersonsMcqAnswersTable.deleteWhere { PersonsMcqAnswersTable.id eq id } > 0
    }
}