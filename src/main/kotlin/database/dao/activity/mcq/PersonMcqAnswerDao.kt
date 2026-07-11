package com.fathersprophets.backend.database.dao.activity.mcq

import com.fathersprophets.backend.database.tables.PersonsMcqAnswersTable
import com.fathersprophets.backend.models.dto.PersonMcqAnswerDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonMcqAnswerDao {
    private fun resultRowToPersonMcqAnswer(row: ResultRow) = PersonMcqAnswerDto(
        id = row[PersonsMcqAnswersTable.id],
        answer = row[PersonsMcqAnswersTable.answer],
        questionId = row[PersonsMcqAnswersTable.questionId],
        userId = row[PersonsMcqAnswersTable.userId],
        status = row[PersonsMcqAnswersTable.status]
    )

    fun findAll() = transaction {
        PersonsMcqAnswersTable.selectAll().map { resultRowToPersonMcqAnswer(it) }
    }

    fun findById(id: Int) = transaction {
        PersonsMcqAnswersTable.selectAll().where { PersonsMcqAnswersTable.id eq id }
            .singleOrNull()?.let { resultRowToPersonMcqAnswer(it) }
    }

    fun findByQuestionId(questionId: Int) = transaction {
        PersonsMcqAnswersTable.selectAll().where { PersonsMcqAnswersTable.questionId eq questionId }
            .map { resultRowToPersonMcqAnswer(it) }
    }

    fun findByUserId(userId: Int) = transaction {
        PersonsMcqAnswersTable.selectAll().where { PersonsMcqAnswersTable.userId eq userId }
            .map { resultRowToPersonMcqAnswer(it) }
    }

    fun create(dto: PersonMcqAnswerDto) = transaction {
        PersonsMcqAnswersTable.insert {
            it[answer] = dto.answer
            it[questionId] = dto.questionId
            it[userId] = dto.userId
            it[status] = dto.status
        } get PersonsMcqAnswersTable.id
    }

    fun update(dto: PersonMcqAnswerDto) = transaction {
        PersonsMcqAnswersTable.update({ PersonsMcqAnswersTable.id eq dto.id }) {
            it[answer] = dto.answer
            it[questionId] = dto.questionId
            it[userId] = dto.userId
            it[status] = dto.status
        } > 0
    }

    fun updateStatus(dto: PersonMcqAnswerDto) = transaction {
        PersonsMcqAnswersTable.update({ PersonsMcqAnswersTable.id eq dto.id }) {
            it[status] = dto.status
        } > 0
    }

    fun delete(id: Int) = transaction {
        PersonsMcqAnswersTable.deleteWhere { PersonsMcqAnswersTable.id eq id } > 0
    }
}