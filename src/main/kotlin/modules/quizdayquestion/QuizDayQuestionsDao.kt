package com.fathersprophets.backend.modules.quizdayquestion

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class QuizDayQuestionsDao {

    private fun ResultRow.toDto() = QuizDayQuestionDto(
        id = this[QuizDayQuestionsTable.id],
        quizDayId = this[QuizDayQuestionsTable.quizDayId],
        question = this[QuizDayQuestionsTable.question],
        choice1 = this[QuizDayQuestionsTable.choice1],
        choice2 = this[QuizDayQuestionsTable.choice2],
        choice3 = this[QuizDayQuestionsTable.choice3],
        choice4 = this[QuizDayQuestionsTable.choice4],
        correctAnswer = this[QuizDayQuestionsTable.correctAnswer]
    )

    fun getAll() = transaction {
        QuizDayQuestionsTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        QuizDayQuestionsTable.selectAll()
            .where { QuizDayQuestionsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByQuizDayId(quizDayId: Int) = transaction {
        QuizDayQuestionsTable.selectAll()
            .where { QuizDayQuestionsTable.quizDayId eq quizDayId }
            .map { it.toDto() }
    }

    fun create(dto: QuizDayQuestionCreateDto) = transaction {
        QuizDayQuestionsTable.insert {
            it[quizDayId] = dto.quizDayId
            it[question] = dto.question
            it[choice1] = dto.choice1
            it[choice2] = dto.choice2
            it[choice3] = dto.choice3
            it[choice4] = dto.choice4
            it[correctAnswer] = dto.correctAnswer
        }.let { getById(it[QuizDayQuestionsTable.id]) }
    }

    fun update(id: Int, dto: QuizDayQuestionUpdateDto) = transaction {
        QuizDayQuestionsTable.update({ QuizDayQuestionsTable.id eq id }) { updateStatement ->
            dto.quizDayId?.let { updateStatement[QuizDayQuestionsTable.quizDayId] = it }
            dto.question?.let { updateStatement[QuizDayQuestionsTable.question] = it }
            dto.choice1?.let { updateStatement[QuizDayQuestionsTable.choice1] = it }
            dto.choice2?.let { updateStatement[QuizDayQuestionsTable.choice2] = it }
            dto.choice3?.let { updateStatement[QuizDayQuestionsTable.choice3] = it }
            dto.choice4?.let { updateStatement[QuizDayQuestionsTable.choice4] = it }
            dto.correctAnswer?.let { updateStatement[QuizDayQuestionsTable.correctAnswer] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        QuizDayQuestionsTable.deleteWhere { QuizDayQuestionsTable.id eq id } > 0
    }
}