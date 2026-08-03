package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.personcomplete.PersonsQuestionsTable
import com.fathersprophets.backend.models.dto.PersonQuestionDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class PersonQuestionDao {
    private fun resultRowToPersonQuestion(row: ResultRow) = PersonQuestionDto(
        id = row[PersonsQuestionsTable.id],
        question = row[PersonsQuestionsTable.question],
        personId = row[PersonsQuestionsTable.personId],
        type = row[PersonsQuestionsTable.type],
        correctAnswer = ""
    )

    fun findAll() = transaction {
        PersonsQuestionsTable.selectAll().map { resultRowToPersonQuestion(it) }
    }

    fun findById(id: Int) = transaction {
        PersonsQuestionsTable.selectAll().where { PersonsQuestionsTable.id eq id }
            .singleOrNull()?.let { resultRowToPersonQuestion(it) }
    }

    fun findByPersonId(personId: Int) = transaction {
        PersonsQuestionsTable.selectAll().where { PersonsQuestionsTable.personId eq personId }
            .map { resultRowToPersonQuestion(it) }
    }

    fun create(personQuestionDto: PersonQuestionDto) = transaction {
        PersonsQuestionsTable.insert {
            it[question] = personQuestionDto.question
            it[personId] = personQuestionDto.personId
            it[type] = personQuestionDto.type
        }.let { findById(it[PersonsQuestionsTable.id]) }
    }

    fun update(personQuestionDto: PersonQuestionDto) = transaction {
        PersonsQuestionsTable.update({ PersonsQuestionsTable.id eq personQuestionDto.id }) {
            it[question] = personQuestionDto.question
            it[personId] = personQuestionDto.personId
            it[type] = personQuestionDto.type
        }.let { findById(personQuestionDto.id) }
    }

    fun delete(personQuestionId : Int) = transaction {
        PersonsQuestionsTable.deleteWhere { PersonsQuestionsTable.id eq personQuestionId } > 0
    }
}