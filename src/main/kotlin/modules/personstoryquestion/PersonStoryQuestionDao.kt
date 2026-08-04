package com.fathersprophets.backend.modules.personstoryquestion

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonStoryQuestionDao {

    private fun ResultRow.toDto() = PersonStoryQuestionDto(
        id = this[PersonStoryQuestionsTable.id],
        storyId = this[PersonStoryQuestionsTable.storyId],
        question = this[PersonStoryQuestionsTable.question],
        correctAnswer = this[PersonStoryQuestionsTable.correctAnswer]
    )

    fun getAll() = transaction {
        PersonStoryQuestionsTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        PersonStoryQuestionsTable.selectAll()
            .where { PersonStoryQuestionsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByStoryId(storyId: Int) = transaction {
        PersonStoryQuestionsTable.selectAll()
            .where { PersonStoryQuestionsTable.storyId eq storyId }
            .map { it.toDto() }
    }

    fun create(dto: PersonStoryQuestionCreateDto) = transaction {
        PersonStoryQuestionsTable.insert {
            it[storyId] = dto.storyId
            it[question] = dto.question
            it[correctAnswer] = dto.correctAnswer
        }.let { getById(it[PersonStoryQuestionsTable.id]) }
    }

    fun update(id: Int, dto: PersonStoryQuestionUpdateDto) = transaction {
        PersonStoryQuestionsTable.update({ PersonStoryQuestionsTable.id eq id }) { updateStatement ->
            dto.storyId?.let { updateStatement[PersonStoryQuestionsTable.storyId] = it }
            dto.question?.let { updateStatement[PersonStoryQuestionsTable.question] = it }
            dto.correctAnswer?.let { updateStatement[PersonStoryQuestionsTable.correctAnswer] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        PersonStoryQuestionsTable.deleteWhere { PersonStoryQuestionsTable.id eq id } > 0
    }
}