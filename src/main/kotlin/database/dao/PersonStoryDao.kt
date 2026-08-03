package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.personstory.PersonStoryTable
import com.fathersprophets.backend.models.dto.PersonStoryDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class PersonStoryDao {
    private fun resultRowToPersonStory(row: ResultRow) = PersonStoryDto(
        id = row[PersonStoryTable.id],
        personId = row[PersonStoryTable.personId],
        title = row[PersonStoryTable.title],
        content = row[PersonStoryTable.content],
        image = row[PersonStoryTable.image],
        video = row[PersonStoryTable.video]
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
            it[video] = dto.video
        }.let { findById(it[PersonStoryTable.id]) }
    }

    fun update(dto: PersonStoryDto) = transaction {
        PersonStoryTable.update({ PersonStoryTable.id eq dto.id }) {
            it[personId] = dto.personId
            it[title] = dto.title
            it[content] = dto.content
            it[image] = dto.image
            it[video] = dto.video
        }.let { findById(dto.id) }
    }

    fun delete(id: Int) = transaction {
        PersonStoryTable.deleteWhere { PersonStoryTable.id eq id } > 0
    }
}