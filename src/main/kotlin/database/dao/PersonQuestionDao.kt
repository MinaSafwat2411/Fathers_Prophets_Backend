package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.PersonsQuestionsTable
import com.fathersprophets.backend.models.dto.PersonQuestionDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonQuestionDao {
    private fun resultRowToPersonQuestion(row: ResultRow) = PersonQuestionDto(
        id = row[PersonsQuestionsTable.id],
        question = row[PersonsQuestionsTable.question],
        personId = row[PersonsQuestionsTable.personId],
        type = row[PersonsQuestionsTable.type]
    )

    fun findAll() = transaction {
        PersonsQuestionsTable.selectAll().map { resultRowToPersonQuestion(it) }
    }

    fun findById(personQuestionDto: PersonQuestionDto) = transaction {
        PersonsQuestionsTable.selectAll().where { PersonsQuestionsTable.id eq personQuestionDto.id }
            .singleOrNull()?.let { resultRowToPersonQuestion(it) }
    }

    fun findByPersonId(personQuestionDto: PersonQuestionDto) = transaction {
        PersonsQuestionsTable.selectAll().where { PersonsQuestionsTable.personId eq personQuestionDto.personId }
            .map { resultRowToPersonQuestion(it) }
    }

    fun create(personQuestionDto: PersonQuestionDto) = transaction {
        PersonsQuestionsTable.insert {
            it[question] = personQuestionDto.question
            it[personId] = personQuestionDto.personId
            it[type] = personQuestionDto.type
        } get PersonsQuestionsTable.id
    }

    fun update(personQuestionDto: PersonQuestionDto) = transaction {
        PersonsQuestionsTable.update({ PersonsQuestionsTable.id eq personQuestionDto.id }) {
            it[question] = personQuestionDto.question
            it[personId] = personQuestionDto.personId
            it[type] = personQuestionDto.type
        } > 0
    }

    fun delete(personQuestionDto: PersonQuestionDto) = transaction {
        PersonsQuestionsTable.deleteWhere { PersonsQuestionsTable.id eq personQuestionDto.id } > 0
    }
}
