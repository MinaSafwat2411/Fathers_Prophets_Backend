package com.fathersprophets.backend.modules.personstory


import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonStoryDao {

    private fun ResultRow.toDto() = PersonStoryDto(
        id = this[PersonStoryTable.id],
        personId = this[PersonStoryTable.personId],
        title = this[PersonStoryTable.title],
        content = this[PersonStoryTable.content],
        image = this[PersonStoryTable.image],
        video = this[PersonStoryTable.video]
    )

    fun getAll() = transaction {
        PersonStoryTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        PersonStoryTable.selectAll()
            .where { PersonStoryTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByPersonId(personId: Int) = transaction {
        PersonStoryTable.selectAll()
            .where { PersonStoryTable.personId eq personId }
            .map { it.toDto() }
    }

    fun create(dto: PersonStoryCreateDto) = transaction {
        PersonStoryTable.insert {
            it[personId] = dto.personId
            it[title] = dto.title
            it[content] = dto.content
            it[image] = dto.image
            it[video] = dto.video
        }.let { getById(it[PersonStoryTable.id]) }
    }

    fun update(id: Int, dto: PersonStoryUpdateDto) = transaction {
        PersonStoryTable.update({ PersonStoryTable.id eq id }) { updateStatement ->
            dto.personId?.let { updateStatement[PersonStoryTable.personId] = it }
            dto.title?.let { updateStatement[PersonStoryTable.title] = it }
            dto.content?.let { updateStatement[PersonStoryTable.content] = it }
            dto.image?.let { updateStatement[PersonStoryTable.image] = it }
            dto.video?.let { updateStatement[PersonStoryTable.video] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        PersonStoryTable.deleteWhere { PersonStoryTable.id eq id } > 0
    }
}