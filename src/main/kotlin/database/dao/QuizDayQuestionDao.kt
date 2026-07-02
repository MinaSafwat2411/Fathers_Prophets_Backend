package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.QuizDayQuestionsTable
import com.fathersprophets.backend.models.dto.QuizDayQuestionDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class QuizDayQuestionDao {

    private fun resultRowToDto(row: ResultRow) = QuizDayQuestionDto(
        id = row[QuizDayQuestionsTable.id],
        quizDayId = row[QuizDayQuestionsTable.quizDayId],
        question = row[QuizDayQuestionsTable.question],
        choice1 = row[QuizDayQuestionsTable.choice1],
        choice2 = row[QuizDayQuestionsTable.choice2],
        choice3 = row[QuizDayQuestionsTable.choice3],
        choice4 = row[QuizDayQuestionsTable.choice4],
        correctAnswer = row[QuizDayQuestionsTable.correctAnswer]
    )

    fun findAll() = transaction {
        QuizDayQuestionsTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        QuizDayQuestionsTable.selectAll().where { QuizDayQuestionsTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun findByQuizDayId(quizDayId: Int) = transaction {
        QuizDayQuestionsTable.selectAll().where { QuizDayQuestionsTable.quizDayId eq quizDayId }
            .map { resultRowToDto(it) }
    }

    fun create(dto: QuizDayQuestionDto) = transaction {
        QuizDayQuestionsTable.insert {
            it[quizDayId] = dto.quizDayId
            it[question] = dto.question
            it[choice1] = dto.choice1
            it[choice2] = dto.choice2
            it[choice3] = dto.choice3
            it[choice4] = dto.choice4
            it[correctAnswer] = dto.correctAnswer
        } get QuizDayQuestionsTable.id
    }

    fun createMany(dtos: List<QuizDayQuestionDto>) = transaction {
        QuizDayQuestionsTable.batchInsert(dtos) { dto ->
            this[QuizDayQuestionsTable.quizDayId] = dto.quizDayId
            this[QuizDayQuestionsTable.question] = dto.question
            this[QuizDayQuestionsTable.choice1] = dto.choice1
            this[QuizDayQuestionsTable.choice2] = dto.choice2
            this[QuizDayQuestionsTable.choice3] = dto.choice3
            this[QuizDayQuestionsTable.choice4] = dto.choice4
            this[QuizDayQuestionsTable.correctAnswer] = dto.correctAnswer
        }.map { resultRowToDto(it) }
    }

    fun update(dto: QuizDayQuestionDto) = transaction {
        QuizDayQuestionsTable.update({ QuizDayQuestionsTable.id eq dto.id }) {
            it[quizDayId] = dto.quizDayId
            it[question] = dto.question
            it[choice1] = dto.choice1
            it[choice2] = dto.choice2
            it[choice3] = dto.choice3
            it[choice4] = dto.choice4
            it[correctAnswer] = dto.correctAnswer
        } > 0
    }

    fun delete(dto: QuizDayQuestionDto) = transaction {
        QuizDayQuestionsTable.deleteWhere { QuizDayQuestionsTable.id eq dto.id } > 0
    }
}