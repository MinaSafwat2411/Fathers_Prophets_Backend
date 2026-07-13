package com.fathersprophets.backend.database.dao.quiz

import com.fathersprophets.backend.database.tables.quiz.QuizTable
import com.fathersprophets.backend.models.dto.QuizDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class QuizDao {

    private fun resultRowToDto(row: ResultRow) = QuizDto(
        id = row[QuizTable.id],
        number = row[QuizTable.number],
        startAt = row[QuizTable.startAt],
        endAt = row[QuizTable.endAt]
    )

    fun findAll() = transaction {
        QuizTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        QuizTable.selectAll().where { QuizTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun create(dto: QuizDto) = transaction {
        QuizTable.insert {
            it[number] = dto.number
            it[startAt] = dto.startAt
            it[endAt] = dto.endAt
        }.let { findById(it[QuizTable.id]) }
    }

    fun update(dto: QuizDto) = transaction {
        QuizTable.update({ QuizTable.id eq dto.id }) {
            it[number] = dto.number
            it[startAt] = dto.startAt
            it[endAt] = dto.endAt
        }.let { findById(dto.id) }
    }

    fun delete(id: Int) = transaction {
        QuizTable.deleteWhere { QuizTable.id eq id } > 0
    }
}