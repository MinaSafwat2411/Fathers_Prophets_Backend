package com.fathersprophets.backend.database.dao.activity.complete

import com.fathersprophets.backend.database.tables.person.complete.PersonsAnswersTable
import com.fathersprophets.backend.models.dto.PersonAnswerDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonAnswerDao {
    private fun resultRowToPersonAnswer(row: ResultRow) = PersonAnswerDto(
        id = row[PersonsAnswersTable.id],
        answer = row[PersonsAnswersTable.answer],
        questionId = row[PersonsAnswersTable.questionId],
        userId = row[PersonsAnswersTable.userId],
        status = row[PersonsAnswersTable.status]
    )

    fun findAll() = transaction {
        PersonsAnswersTable.selectAll().map { resultRowToPersonAnswer(it) }
    }

    fun findById(id: Int) = transaction {
        PersonsAnswersTable.selectAll().where { PersonsAnswersTable.id eq id }
            .singleOrNull()?.let { resultRowToPersonAnswer(it) }
    }

    fun findByQuestionId(questionId: Int) = transaction {
        PersonsAnswersTable.selectAll().where { PersonsAnswersTable.questionId eq questionId }
            .map { resultRowToPersonAnswer(it) }
    }

    fun findByUserId(userId: Int) = transaction {
        PersonsAnswersTable.selectAll().where { PersonsAnswersTable.userId eq userId }
            .map { resultRowToPersonAnswer(it) }
    }

    fun create(dto: PersonAnswerDto) = transaction {
        PersonsAnswersTable.insert {
            it[answer] = dto.answer
            it[questionId] = dto.questionId
            it[userId] = dto.userId
            it[status] = dto.status
        } get PersonsAnswersTable.id
    }

    fun update(dto: PersonAnswerDto) = transaction {
        PersonsAnswersTable.update({ PersonsAnswersTable.id eq dto.id }) {
            it[answer] = dto.answer
            it[questionId] = dto.questionId
            it[userId] = dto.userId
            it[status] = dto.status
        } > 0
    }

    fun updateStatus(dto: PersonAnswerDto) = transaction {
        PersonsAnswersTable.update({ PersonsAnswersTable.id eq dto.id }) {
            it[status] = dto.status
        } > 0
    }

    fun delete(id: Int) = transaction {
        PersonsAnswersTable.deleteWhere { PersonsAnswersTable.id eq id } > 0
    }
}