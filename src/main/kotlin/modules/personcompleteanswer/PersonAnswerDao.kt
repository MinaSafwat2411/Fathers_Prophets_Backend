package com.fathersprophets.backend.modules.personcompleteanswer

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonAnswerDao : CrudDao<PersonAnswerDto, PersonAnswerCreateDto, PersonAnswerUpdateDto> {

    private fun ResultRow.toDto() = PersonAnswerDto(
        id = this[PersonsAnswersTable.id],
        answer = this[PersonsAnswersTable.answer],
        questionId = this[PersonsAnswersTable.questionId],
        userId = this[PersonsAnswersTable.userId],
        status = this[PersonsAnswersTable.status]
    )

    override fun getAll() = transaction {
        PersonsAnswersTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        PersonsAnswersTable.selectAll()
            .where { PersonsAnswersTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByQuestionId(questionId: Int) = transaction {
        PersonsAnswersTable.selectAll()
            .where { PersonsAnswersTable.questionId eq questionId }
            .map { it.toDto() }
    }

    fun getByUserId(userId: Int) = transaction {
        PersonsAnswersTable.selectAll()
            .where { PersonsAnswersTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByQuestionAndUser(questionId: Int, userId: Int) = transaction {
        PersonsAnswersTable.selectAll()
            .where { (PersonsAnswersTable.questionId eq questionId) and (PersonsAnswersTable.userId eq userId) }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: PersonAnswerCreateDto) = transaction {
        PersonsAnswersTable.insert {
            it[answer] = dto.answer
            it[questionId] = dto.questionId
            it[userId] = dto.userId
            it[status] = dto.status
        }.let { getById(it[PersonsAnswersTable.id]) }
    }

    override fun update(id: Int, dto: PersonAnswerUpdateDto) = transaction {
        PersonsAnswersTable.update({ PersonsAnswersTable.id eq id }) { updateStatement ->
            dto.answer?.let { updateStatement[PersonsAnswersTable.answer] = it }
            dto.questionId?.let { updateStatement[PersonsAnswersTable.questionId] = it }
            dto.userId?.let { updateStatement[PersonsAnswersTable.userId] = it }
            dto.status?.let { updateStatement[PersonsAnswersTable.status] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        PersonsAnswersTable.deleteWhere { PersonsAnswersTable.id eq id } > 0
    }
}