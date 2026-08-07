package com.fathersprophets.backend.modules.personstory

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonStoryDao : CrudDao<PersonStoryDto, PersonStoryCreateDto, PersonStoryUpdateDto> {

    private fun ResultRow.toDto() = PersonStoryDto(
        id = this[PersonStoryTable.id],
        personId = this[PersonStoryTable.personId],
        title = this[PersonStoryTable.title],
        content = this[PersonStoryTable.content],
        image = this[PersonStoryTable.image],
        video = this[PersonStoryTable.video]
    )

    override fun getAll() = transaction {
        PersonStoryTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
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

    override fun create(dto: PersonStoryCreateDto) = transaction {
        PersonStoryTable.insert {
            it[personId] = dto.personId
            it[title] = dto.title
            it[content] = dto.content
            it[image] = dto.image
            it[video] = dto.video
        }.let { getById(it[PersonStoryTable.id]) }
    }

    override fun update(id: Int, dto: PersonStoryUpdateDto) = transaction {
        PersonStoryTable.update({ PersonStoryTable.id eq id }) { updateStatement ->
            dto.personId?.let { updateStatement[PersonStoryTable.personId] = it }
            dto.title?.let { updateStatement[PersonStoryTable.title] = it }
            dto.content?.let { updateStatement[PersonStoryTable.content] = it }
            dto.image?.let { updateStatement[PersonStoryTable.image] = it }
            dto.video?.let { updateStatement[PersonStoryTable.video] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        PersonStoryTable.deleteWhere { PersonStoryTable.id eq id } > 0
    }
}