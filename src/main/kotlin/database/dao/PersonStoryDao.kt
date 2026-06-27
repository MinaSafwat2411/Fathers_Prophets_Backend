package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.PersonStoryTable
import com.fathersprophets.backend.models.dto.PersonStoryDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonStoryDao {
    private fun resultRowToPersonStory(row: ResultRow) = PersonStoryDto(
        id = row[PersonStoryTable.id],
        personId = row[PersonStoryTable.personId],
        title = row[PersonStoryTable.title],
        content = row[PersonStoryTable.content],
        image = row[PersonStoryTable.image],
        question = row[PersonStoryTable.question]
    )

    fun findAll() = transaction {
        PersonStoryTable.selectAll().map { resultRowToPersonStory(it) }
    }

    fun findById(id: Int) = transaction {
        PersonStoryTable.selectAll().where { PersonStoryTable.id eq id }
            .singleOrNull()?.let { resultRowToPersonStory(it) }
    }

    fun findByPersonId(personId: Int) = transaction {
        PersonStoryTable.selectAll().where { PersonStoryTable.personId eq personId }
            .map { resultRowToPersonStory(it) }
    }

    fun create(dto: PersonStoryDto) = transaction {
        PersonStoryTable.insert {
            it[personId] = dto.personId
            it[title] = dto.title
            it[content] = dto.content
            it[image] = dto.image
            it[question] = dto.question
        } get PersonStoryTable.id
    }

    fun update(dto: PersonStoryDto) = transaction {
        PersonStoryTable.update({ PersonStoryTable.id eq dto.id }) {
            it[personId] = dto.personId
            it[title] = dto.title
            it[content] = dto.content
            it[image] = dto.image
            it[question] = dto.question
        } > 0
    }

    fun delete(dto: PersonStoryDto) = transaction {
        PersonStoryTable.deleteWhere { PersonStoryTable.id eq dto.id } > 0
    }
}
