package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.PersonsMcqTable
import com.fathersprophets.backend.models.dto.PersonMcqDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonMcqDao {
    private fun resultRowToPersonMcq(row: ResultRow) = PersonMcqDto(
        id = row[PersonsMcqTable.id],
        questionId = row[PersonsMcqTable.questionId],
        question = row[PersonsMcqTable.question],
        first = row[PersonsMcqTable.first],
        second = row[PersonsMcqTable.second],
        third = row[PersonsMcqTable.third],
        fourth = row[PersonsMcqTable.fourth],
        correctAnswer = row[PersonsMcqTable.correctAnswer]
    )

    fun findAll() = transaction {
        PersonsMcqTable.selectAll().map { resultRowToPersonMcq(it) }
    }

    fun findById(id: Int) = transaction {
        PersonsMcqTable.selectAll().where { PersonsMcqTable.id eq id }
            .singleOrNull()?.let { resultRowToPersonMcq(it) }
    }

    fun findByQuestionId(questionId: Int) = transaction {
        PersonsMcqTable.selectAll().where { PersonsMcqTable.questionId eq questionId }
            .map { resultRowToPersonMcq(it) }
    }

    fun create(dto: PersonMcqDto) = transaction {
        PersonsMcqTable.insert {
            it[questionId] = dto.questionId
            it[question] = dto.question
            it[first] = dto.first
            it[second] = dto.second
            it[third] = dto.third
            it[fourth] = dto.fourth
            it[correctAnswer] = dto.correctAnswer
        } get PersonsMcqTable.id
    }

    fun update(dto: PersonMcqDto) = transaction {
        PersonsMcqTable.update({ PersonsMcqTable.id eq dto.id }) {
            it[questionId] = dto.questionId
            it[question] = dto.question
            it[first] = dto.first
            it[second] = dto.second
            it[third] = dto.third
            it[fourth] = dto.fourth
            it[correctAnswer] = dto.correctAnswer
        } > 0
    }

    fun delete(dto: PersonMcqDto) = transaction {
        PersonsMcqTable.deleteWhere { PersonsMcqTable.id eq dto.id } > 0
    }
}