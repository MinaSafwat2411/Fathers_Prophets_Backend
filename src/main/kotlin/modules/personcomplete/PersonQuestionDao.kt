package com.fathersprophets.backend.modules.personcomplete

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonQuestionDao : CrudDao<PersonQuestionDto, PersonQuestionCreateDto, PersonQuestionUpdateDto> {

    private fun ResultRow.toDto() = PersonQuestionDto(
        id = this[PersonsQuestionsTable.id],
        question = this[PersonsQuestionsTable.question],
        personId = this[PersonsQuestionsTable.personId],
        correctAnswer = this[PersonsQuestionsTable.correctAnswer]
    )

    override fun getAll() = transaction {
        PersonsQuestionsTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        PersonsQuestionsTable.selectAll()
            .where { PersonsQuestionsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByPersonId(personId: Int) = transaction {
        PersonsQuestionsTable.selectAll()
            .where { PersonsQuestionsTable.personId eq personId }
            .map { it.toDto() }
    }

    override fun create(dto: PersonQuestionCreateDto) = transaction {
        PersonsQuestionsTable.insert {
            it[question] = dto.question
            it[personId] = dto.personId
            it[correctAnswer] = dto.correctAnswer
        }.let { getById(it[PersonsQuestionsTable.id]) }
    }

    override fun update(id: Int, dto: PersonQuestionUpdateDto) = transaction {
        PersonsQuestionsTable.update({ PersonsQuestionsTable.id eq id }) { updateStatement ->
            dto.question?.let { updateStatement[PersonsQuestionsTable.question] = it }
            dto.personId?.let { updateStatement[PersonsQuestionsTable.personId] = it }
            dto.correctAnswer?.let { updateStatement[PersonsQuestionsTable.correctAnswer] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        PersonsQuestionsTable.deleteWhere { PersonsQuestionsTable.id eq id } > 0
    }
}