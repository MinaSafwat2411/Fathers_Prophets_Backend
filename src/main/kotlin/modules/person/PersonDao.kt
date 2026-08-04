package com.fathersprophets.backend.modules.person


import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonDao {

    private fun ResultRow.toDto() = PersonDto(
        id = this[PersonsTable.id],
        name = this[PersonsTable.name],
        nickname = this[PersonsTable.nickname],
        shortStory = this[PersonsTable.shortStory],
        fullStory = this[PersonsTable.fullStory],
        image = this[PersonsTable.image],
        type = this[PersonsTable.type]
    )

    fun getAll() = transaction {
        PersonsTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        PersonsTable.selectAll()
            .where { PersonsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByType(type: com.fathersprophets.backend.database.enums.PersonType) = transaction {
        PersonsTable.selectAll()
            .where { PersonsTable.type eq type }
            .map { it.toDto() }
    }

    fun create(dto: PersonCreateDto) = transaction {
        PersonsTable.insert {
            it[name] = dto.name
            it[nickname] = dto.nickname
            it[shortStory] = dto.shortStory
            it[fullStory] = dto.fullStory
            it[image] = dto.image
            it[type] = dto.type
        }.let { getById(it[PersonsTable.id]) }
    }

    fun update(id: Int, dto: PersonUpdateDto) = transaction {
        PersonsTable.update({ PersonsTable.id eq id }) { updateStatement ->
            dto.name?.let { updateStatement[PersonsTable.name] = it }
            dto.nickname?.let { updateStatement[PersonsTable.nickname] = it }
            dto.shortStory?.let { updateStatement[PersonsTable.shortStory] = it }
            dto.fullStory?.let { updateStatement[PersonsTable.fullStory] = it }
            dto.image?.let { updateStatement[PersonsTable.image] = it }
            dto.type?.let { updateStatement[PersonsTable.type] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        PersonsTable.deleteWhere { PersonsTable.id eq id } > 0
    }
}