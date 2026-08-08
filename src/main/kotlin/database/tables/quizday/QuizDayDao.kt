package com.fathersprophets.backend.database.tables.quizday

import com.fathersprophets.backend.base.CrudDao
import com.fathersprophets.backend.database.enums.DayOfWeek

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class QuizDayDao : CrudDao<QuizDayDto, QuizDayCreateDto, QuizDayUpdateDto> {

    private fun ResultRow.toDto() = QuizDayDto(
        id = this[QuizDayTable.id],
        quizId = this[QuizDayTable.quizId],
        dayName = this[QuizDayTable.dayName],
        book = this[QuizDayTable.book],
        chapter = this[QuizDayTable.chapter],
        verseFrom = this[QuizDayTable.verseFrom],
        verseTo = this[QuizDayTable.verseTo],
        typeDay = this[QuizDayTable.typeDay]
    )

    override fun getAll() = transaction {
        QuizDayTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        QuizDayTable.selectAll()
            .where { QuizDayTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByQuizId(quizId: Int) = transaction {
        QuizDayTable.selectAll()
            .where { QuizDayTable.quizId eq quizId }
            .map { it.toDto() }
    }

    fun getByQuizAndDay(quizId: Int, dayName: DayOfWeek) = transaction {
        QuizDayTable.selectAll()
            .where { (QuizDayTable.quizId eq quizId) and (QuizDayTable.dayName eq dayName) }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: QuizDayCreateDto) = transaction {
        QuizDayTable.insert {
            it[quizId] = dto.quizId
            it[dayName] = dto.dayName
            it[book] = dto.book
            it[chapter] = dto.chapter
            it[verseFrom] = dto.verseFrom
            it[verseTo] = dto.verseTo
            it[typeDay] = dto.typeDay
        }.let { getById(it[QuizDayTable.id]) }
    }

    override fun update(id: Int, dto: QuizDayUpdateDto) = transaction {
        QuizDayTable.update({ QuizDayTable.id eq id }) { updateStatement ->
            dto.quizId?.let { updateStatement[QuizDayTable.quizId] = it }
            dto.dayName?.let { updateStatement[QuizDayTable.dayName] = it }
            dto.book?.let { updateStatement[QuizDayTable.book] = it }
            dto.chapter?.let { updateStatement[QuizDayTable.chapter] = it }
            dto.verseFrom?.let { updateStatement[QuizDayTable.verseFrom] = it }
            dto.verseTo?.let { updateStatement[QuizDayTable.verseTo] = it }
            dto.typeDay?.let { updateStatement[QuizDayTable.typeDay] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        QuizDayTable.deleteWhere { QuizDayTable.id eq id } > 0
    }
}