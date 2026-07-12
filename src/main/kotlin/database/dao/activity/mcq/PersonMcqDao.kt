package com.fathersprophets.backend.database.dao.activity.mcq

import com.fathersprophets.backend.database.tables.person.mcq.PersonsMcqTable
import com.fathersprophets.backend.models.dto.PersonMcqDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonMcqDao {
    private fun resultRowToPersonMcq(row: ResultRow) = PersonMcqDto(
        id = row[PersonsMcqTable.id],
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

    fun findByPersonId(personId: Int) = transaction {
        PersonsMcqTable.selectAll().where { PersonsMcqTable.personId eq personId }
    }

    fun create(dto: PersonMcqDto) = transaction {
        PersonsMcqTable.insert {
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
            it[question] = dto.question
            it[first] = dto.first
            it[second] = dto.second
            it[third] = dto.third
            it[fourth] = dto.fourth
            it[correctAnswer] = dto.correctAnswer
        } > 0
    }

    fun delete(id: Int) = transaction {
        PersonsMcqTable.deleteWhere { PersonsMcqTable.id eq id } > 0
    }
}