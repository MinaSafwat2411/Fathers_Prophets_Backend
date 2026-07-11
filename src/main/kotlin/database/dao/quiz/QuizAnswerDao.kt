package com.fathersprophets.backend.database.dao.quiz

import com.fathersprophets.backend.database.tables.QuizAnswersTable
import com.fathersprophets.backend.models.dto.QuizAnswerDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class QuizAnswerDao {

    private fun resultRowToDto(row: ResultRow) = QuizAnswerDto(
        id = row[QuizAnswersTable.id],
        quizId = row[QuizAnswersTable.quizId],
        questionId = row[QuizAnswersTable.questionId],
        dayId = row[QuizAnswersTable.dayId],
        userId = row[QuizAnswersTable.userId],
        answer = row[QuizAnswersTable.answer],
        status = row[QuizAnswersTable.status]
    )

    fun findAll() = transaction {
        QuizAnswersTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        QuizAnswersTable.selectAll().where { QuizAnswersTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun findByQuestionId(questionId: Int) = transaction {
        QuizAnswersTable.selectAll().where { QuizAnswersTable.questionId eq questionId }
            .map { resultRowToDto(it) }
    }

    fun findByUserId(userId: Int) = transaction {
        QuizAnswersTable.selectAll().where { QuizAnswersTable.userId eq userId }
            .map { resultRowToDto(it) }
    }

    fun findByDayId(dayId: Int) = transaction {
        QuizAnswersTable.selectAll().where { QuizAnswersTable.dayId eq dayId }
            .map { resultRowToDto(it) }
    }

    fun findByQuizId(quizId: Int) = transaction {
        QuizAnswersTable.selectAll().where { QuizAnswersTable.quizId eq quizId }
            .map { resultRowToDto(it) }
    }

    fun create(dto: QuizAnswerDto) = transaction {
        QuizAnswersTable.insert {
            it[quizId] = dto.quizId
            it[questionId] = dto.questionId
            it[dayId] = dto.dayId
            it[userId] = dto.userId
            it[answer] = dto.answer
            it[status] = dto.status
        } get QuizAnswersTable.id
    }

    fun createMany(dtos: List<QuizAnswerDto>) = transaction {
        QuizAnswersTable.batchInsert(dtos) { dto ->
            this[QuizAnswersTable.quizId] = dto.quizId
            this[QuizAnswersTable.questionId] = dto.questionId
            this[QuizAnswersTable.dayId] = dto.dayId
            this[QuizAnswersTable.userId] = dto.userId
            this[QuizAnswersTable.answer] = dto.answer
            this[QuizAnswersTable.status] = dto.status
        }.map { resultRowToDto(it) }
    }

    fun update(dto: QuizAnswerDto) = transaction {
        QuizAnswersTable.update({ QuizAnswersTable.id eq dto.id }) {
            it[quizId] = dto.quizId
            it[questionId] = dto.questionId
            it[dayId] = dto.dayId
            it[userId] = dto.userId
            it[answer] = dto.answer
            it[status] = dto.status
        } > 0
    }

    fun delete(id: Int) = transaction {
        QuizAnswersTable.deleteWhere { QuizAnswersTable.id eq id } > 0
    }
}