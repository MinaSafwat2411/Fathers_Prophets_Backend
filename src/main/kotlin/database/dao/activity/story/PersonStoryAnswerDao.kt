package com.fathersprophets.backend.database.dao.activity.story

import com.fathersprophets.backend.database.tables.PersonStoryAnswersTable
import com.fathersprophets.backend.models.dto.PersonStoryAnswerDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonStoryAnswerDao {

    private fun resultRowToDto(row: ResultRow) = PersonStoryAnswerDto(
        id = row[PersonStoryAnswersTable.id],
        storyId = row[PersonStoryAnswersTable.storyId],
        userId = row[PersonStoryAnswersTable.userId],
        answered = row[PersonStoryAnswersTable.answered],
        status = row[PersonStoryAnswersTable.status],
        questionId = row[PersonStoryAnswersTable.questionId]
    )

    fun findAll() = transaction {
        PersonStoryAnswersTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        PersonStoryAnswersTable.selectAll().where { PersonStoryAnswersTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun findByStoryId(storyId: Int) = transaction {
        PersonStoryAnswersTable.selectAll().where { PersonStoryAnswersTable.storyId eq storyId }
            .map { resultRowToDto(it) }
    }

    fun findByUserId(userId: Int) = transaction {
        PersonStoryAnswersTable.selectAll().where { PersonStoryAnswersTable.userId eq userId }
            .map { resultRowToDto(it) }
    }

    fun findByQuestionId(questionId: Int) = transaction {
        PersonStoryAnswersTable.selectAll().where { PersonStoryAnswersTable.questionId eq questionId }
            .map { resultRowToDto(it) }
    }

    fun create(dto: PersonStoryAnswerDto) = transaction {
        PersonStoryAnswersTable.insert {
            it[storyId] = dto.storyId
            it[userId] = dto.userId
            it[answered] = dto.answered
            it[status] = dto.status
            it[questionId] = dto.questionId
        } get PersonStoryAnswersTable.id
    }

    fun update(dto: PersonStoryAnswerDto) = transaction {
        PersonStoryAnswersTable.update({ PersonStoryAnswersTable.id eq dto.id }) {
            it[storyId] = dto.storyId
            it[userId] = dto.userId
            it[answered] = dto.answered
            it[status] = dto.status
            it[questionId] = dto.questionId
        } > 0
    }

    fun updateStatus(dto: PersonStoryAnswerDto) = transaction {
        PersonStoryAnswersTable.update({ PersonStoryAnswersTable.id eq dto.id }) {
            it[status] = dto.status
        } > 0
    }

    fun delete(id: Int) = transaction {
        PersonStoryAnswersTable.deleteWhere { PersonStoryAnswersTable.id eq id } > 0
    }
}