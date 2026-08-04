package com.fathersprophets.backend.modules.personmcq

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonMcqDao {

    private fun ResultRow.toDto() = PersonMcqDto(
        id = this[PersonsMcqTable.id],
        personId = this[PersonsMcqTable.personId],
        question = this[PersonsMcqTable.question],
        first = this[PersonsMcqTable.first],
        second = this[PersonsMcqTable.second],
        third = this[PersonsMcqTable.third],
        fourth = this[PersonsMcqTable.fourth],
        correctAnswer = this[PersonsMcqTable.correctAnswer]
    )

    fun getAll() = transaction {
        PersonsMcqTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        PersonsMcqTable.selectAll()
            .where { PersonsMcqTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByPersonId(personId: Int) = transaction {
        PersonsMcqTable.selectAll()
            .where { PersonsMcqTable.personId eq personId }
            .map { it.toDto() }
    }

    fun create(dto: PersonMcqCreateDto) = transaction {
        PersonsMcqTable.insert {
            it[personId] = dto.personId
            it[question] = dto.question
            it[first] = dto.first
            it[second] = dto.second
            it[third] = dto.third
            it[fourth] = dto.fourth
            it[correctAnswer] = dto.correctAnswer
        }.let { getById(it[PersonsMcqTable.id]) }
    }

    fun update(id: Int, dto: PersonMcqUpdateDto) = transaction {
        PersonsMcqTable.update({ PersonsMcqTable.id eq id }) { updateStatement ->
            dto.personId?.let { updateStatement[PersonsMcqTable.personId] = it }
            dto.question?.let { updateStatement[PersonsMcqTable.question] = it }
            dto.first?.let { updateStatement[PersonsMcqTable.first] = it }
            dto.second?.let { updateStatement[PersonsMcqTable.second] = it }
            dto.third?.let { updateStatement[PersonsMcqTable.third] = it }
            dto.fourth?.let { updateStatement[PersonsMcqTable.fourth] = it }
            dto.correctAnswer?.let { updateStatement[PersonsMcqTable.correctAnswer] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        PersonsMcqTable.deleteWhere { PersonsMcqTable.id eq id } > 0
    }
}