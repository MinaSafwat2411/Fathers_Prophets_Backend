package com.fathersprophets.backend.database.dao.quiz

import com.fathersprophets.backend.database.tables.quiz.QuizDayTable
import com.fathersprophets.backend.models.dto.QuizDayDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class QuizDayDao {

    private fun resultRowToDto(row: ResultRow) = QuizDayDto(
        id = row[QuizDayTable.id],
        quizId = row[QuizDayTable.quizId],
        dayName = row[QuizDayTable.dayName],
        startAt = row[QuizDayTable.startAt],
        endAt = row[QuizDayTable.endAt],
        book = row[QuizDayTable.book],
        chapter = row[QuizDayTable.chapter],
        verseFrom = row[QuizDayTable.verseFrom],
        verseTo = row[QuizDayTable.verseTo],
        typeDay = row[QuizDayTable.typeDay]
    )

    fun findAll() = transaction {
        QuizDayTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        QuizDayTable.selectAll().where { QuizDayTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun findByQuizId(quizId: Int) = transaction {
        QuizDayTable.selectAll().where { QuizDayTable.quizId eq quizId }
            .map { resultRowToDto(it) }
    }

    fun create(dto: QuizDayDto) = transaction {
        QuizDayTable.insert {
            it[quizId] = dto.quizId
            it[dayName] = dto.dayName
            it[startAt] = dto.startAt
            it[endAt] = dto.endAt
            it[book] = dto.book
            it[chapter] = dto.chapter
            it[verseFrom] = dto.verseFrom
            it[verseTo] = dto.verseTo
            it[typeDay] = dto.typeDay
        } get QuizDayTable.id
    }

    fun update(dto: QuizDayDto) = transaction {
        QuizDayTable.update({ QuizDayTable.id eq dto.id }) {
            it[quizId] = dto.quizId
            it[dayName] = dto.dayName
            it[startAt] = dto.startAt
            it[endAt] = dto.endAt
            it[book] = dto.book
            it[chapter] = dto.chapter
            it[verseFrom] = dto.verseFrom
            it[verseTo] = dto.verseTo
            it[typeDay] = dto.typeDay
        } > 0
    }

    fun delete(id: Int) = transaction {
        QuizDayTable.deleteWhere { QuizDayTable.id eq id } > 0
    }
}