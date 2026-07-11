package com.fathersprophets.backend.database.dao.activity.story

import com.fathersprophets.backend.database.tables.PersonStoryQuestionsTable
import com.fathersprophets.backend.models.dto.PersonStoryQuestionDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonStoryQuestionDao {
    private fun resultRowToPersonStoryQuestion(row: ResultRow) = PersonStoryQuestionDto(
        id = row[PersonStoryQuestionsTable.id],
        storyId = row[PersonStoryQuestionsTable.storyId],
        question = row[PersonStoryQuestionsTable.question]
    )

    fun findAll() = transaction {
        PersonStoryQuestionsTable.selectAll().map { resultRowToPersonStoryQuestion(it) }
    }

    fun findById(id: Int) = transaction {
        PersonStoryQuestionsTable.selectAll().where { PersonStoryQuestionsTable.id eq id }
            .singleOrNull()?.let { resultRowToPersonStoryQuestion(it) }
    }

    fun findByStoryId(storyId: Int) = transaction {
        PersonStoryQuestionsTable.selectAll().where { PersonStoryQuestionsTable.storyId eq storyId }
            .map { resultRowToPersonStoryQuestion(it) }
    }

    fun create(dto: PersonStoryQuestionDto) = transaction {
        PersonStoryQuestionsTable.insert {
            it[storyId] = dto.storyId
            it[question] = dto.question
        } get PersonStoryQuestionsTable.id
    }

    fun update(dto: PersonStoryQuestionDto) = transaction {
        PersonStoryQuestionsTable.update({ PersonStoryQuestionsTable.id eq dto.id }) {
            it[storyId] = dto.storyId
            it[question] = dto.question
        } > 0
    }

    fun delete(id: Int) = transaction {
        PersonStoryQuestionsTable.deleteWhere { PersonStoryQuestionsTable.id eq id } > 0
    }
}