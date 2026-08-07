package com.fathersprophets.backend.modules.quizanswers

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class QuizAnswerDao : CrudDao<QuizAnswerDto, QuizAnswerCreateDto, QuizAnswerUpdateDto> {

    private fun ResultRow.toDto() = QuizAnswerDto(
        id = this[QuizAnswersTable.id],
        questionId = this[QuizAnswersTable.questionId],
        userId = this[QuizAnswersTable.userId],
        answer = this[QuizAnswersTable.answer],
        answerOrder = this[QuizAnswersTable.answerOrder],
        status = this[QuizAnswersTable.status]
    )

    override fun getAll() = transaction {
        QuizAnswersTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        QuizAnswersTable.selectAll()
            .where { QuizAnswersTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByQuestionId(questionId: Int) = transaction {
        QuizAnswersTable.selectAll()
            .where { QuizAnswersTable.questionId eq questionId }
            .map { it.toDto() }
    }

    fun getByUserId(userId: Int) = transaction {
        QuizAnswersTable.selectAll()
            .where { QuizAnswersTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByQuestionAndUser(questionId: Int, userId: Int) = transaction {
        QuizAnswersTable.selectAll()
            .where { (QuizAnswersTable.questionId eq questionId) and (QuizAnswersTable.userId eq userId) }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: QuizAnswerCreateDto) = transaction {
        QuizAnswersTable.insert {
            it[questionId] = dto.questionId
            it[userId] = dto.userId
            it[answer] = dto.answer
            it[answerOrder] = dto.answerOrder
            it[status] = dto.status
        }.let { getById(it[QuizAnswersTable.id]) }
    }

    override fun update(id: Int, dto: QuizAnswerUpdateDto) = transaction {
        QuizAnswersTable.update({ QuizAnswersTable.id eq id }) { updateStatement ->
            dto.questionId?.let { updateStatement[QuizAnswersTable.questionId] = it }
            dto.userId?.let { updateStatement[QuizAnswersTable.userId] = it }
            dto.answer?.let { updateStatement[QuizAnswersTable.answer] = it }
            dto.answerOrder?.let { updateStatement[QuizAnswersTable.answerOrder] = it }
            dto.status?.let { updateStatement[QuizAnswersTable.status] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        QuizAnswersTable.deleteWhere { QuizAnswersTable.id eq id } > 0
    }
}