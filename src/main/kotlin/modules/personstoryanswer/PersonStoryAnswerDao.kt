package com.fathersprophets.backend.modules.personstoryanswer

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonStoryAnswerDao {

    private fun ResultRow.toDto() = PersonStoryAnswerDto(
        id = this[PersonStoryAnswersTable.id],
        storyId = this[PersonStoryAnswersTable.storyId],
        userId = this[PersonStoryAnswersTable.userId],
        answered = this[PersonStoryAnswersTable.answered],
        status = this[PersonStoryAnswersTable.status],
        questionId = this[PersonStoryAnswersTable.questionId]
    )

    fun getAll() = transaction {
        PersonStoryAnswersTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        PersonStoryAnswersTable.selectAll()
            .where { PersonStoryAnswersTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByStoryId(storyId: Int) = transaction {
        PersonStoryAnswersTable.selectAll()
            .where { PersonStoryAnswersTable.storyId eq storyId }
            .map { it.toDto() }
    }

    fun getByUserId(userId: Int) = transaction {
        PersonStoryAnswersTable.selectAll()
            .where { PersonStoryAnswersTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByQuestionId(questionId: Int) = transaction {
        PersonStoryAnswersTable.selectAll()
            .where { PersonStoryAnswersTable.questionId eq questionId }
            .map { it.toDto() }
    }

    fun getByStoryUserAndQuestion(storyId: Int, userId: Int, questionId: Int) = transaction {
        PersonStoryAnswersTable.selectAll()
            .where {
                (PersonStoryAnswersTable.storyId eq storyId) and
                        (PersonStoryAnswersTable.userId eq userId) and
                        (PersonStoryAnswersTable.questionId eq questionId)
            }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun create(dto: PersonStoryAnswerCreateDto) = transaction {
        PersonStoryAnswersTable.insert {
            it[storyId] = dto.storyId
            it[userId] = dto.userId
            it[answered] = dto.answered
            it[status] = dto.status
            it[questionId] = dto.questionId
        }.let { getById(it[PersonStoryAnswersTable.id]) }
    }

    fun update(id: Int, dto: PersonStoryAnswerUpdateDto) = transaction {
        PersonStoryAnswersTable.update({ PersonStoryAnswersTable.id eq id }) { updateStatement ->
            dto.storyId?.let { updateStatement[PersonStoryAnswersTable.storyId] = it }
            dto.userId?.let { updateStatement[PersonStoryAnswersTable.userId] = it }
            dto.answered?.let { updateStatement[PersonStoryAnswersTable.answered] = it }
            dto.status?.let { updateStatement[PersonStoryAnswersTable.status] = it }
            dto.questionId?.let { updateStatement[PersonStoryAnswersTable.questionId] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        PersonStoryAnswersTable.deleteWhere { PersonStoryAnswersTable.id eq id } > 0
    }
}