package com.fathersprophets.backend.database.dao.person.story

import com.fathersprophets.backend.database.tables.person.story.PersonStoryAnswersTable
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

    fun findByUserIdAndStoryId(userId: Int, storyId: Int) = transaction {
        PersonStoryAnswersTable.selectAll().where { (PersonStoryAnswersTable.storyId eq storyId) and (PersonStoryAnswersTable.userId eq userId)}
            .map { resultRowToDto(it) }
    }

    fun create(dto: PersonStoryAnswerDto) = transaction {
        PersonStoryAnswersTable.insert {
            it[storyId] = dto.storyId
            it[userId] = dto.userId
            it[answered] = dto.answered
            it[status] = dto.status
            it[questionId] = dto.questionId
        }.let { findById(it[PersonStoryAnswersTable.id]) }
    }

    fun update(dto: PersonStoryAnswerDto) = transaction {
        PersonStoryAnswersTable.update({ PersonStoryAnswersTable.id eq dto.id }) {
            it[storyId] = dto.storyId
            it[userId] = dto.userId
            it[answered] = dto.answered
            it[status] = dto.status
            it[questionId] = dto.questionId
        }.let { findById(dto.id) }
    }

    fun updateStatus(dto: PersonStoryAnswerDto) = transaction {
        PersonStoryAnswersTable.update({ PersonStoryAnswersTable.id eq dto.id }) {
            it[status] = dto.status
        }.let { findById(dto.id) }
    }

    fun delete(id: Int) = transaction {
        PersonStoryAnswersTable.deleteWhere { PersonStoryAnswersTable.id eq id } > 0
    }
}