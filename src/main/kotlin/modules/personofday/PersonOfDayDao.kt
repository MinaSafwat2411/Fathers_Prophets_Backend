package com.fathersprophets.backend.modules.personofday


import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PersonOfDayDao {

    private fun ResultRow.toDto() = PersonOfDayDto(
        id = this[PersonOfDayTable.id],
        personId = this[PersonOfDayTable.personId],
        message = this[PersonOfDayTable.message],
        verse = this[PersonOfDayTable.verse],
        date = this[PersonOfDayTable.date].toString()
    )

    fun getAll() = transaction {
        PersonOfDayTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        PersonOfDayTable.selectAll()
            .where { PersonOfDayTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByPersonId(personId: Int) = transaction {
        PersonOfDayTable.selectAll()
            .where { PersonOfDayTable.personId eq personId }
            .map { it.toDto() }
    }

    fun getByDate(date: String) = transaction {
        PersonOfDayTable.selectAll()
            .where { PersonOfDayTable.date eq java.time.LocalDate.parse(date) }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun create(dto: PersonOfDayCreateDto) = transaction {
        PersonOfDayTable.insert {
            it[personId] = dto.personId
            it[message] = dto.message
            it[verse] = dto.verse
            it[date] = java.time.LocalDate.parse(dto.date)
        }.let { getById(it[PersonOfDayTable.id]) }
    }

    fun update(id: Int, dto: PersonOfDayUpdateDto) = transaction {
        PersonOfDayTable.update({ PersonOfDayTable.id eq id }) { updateStatement ->
            dto.personId?.let { updateStatement[PersonOfDayTable.personId] = it }
            dto.message?.let { updateStatement[PersonOfDayTable.message] = it }
            dto.verse?.let { updateStatement[PersonOfDayTable.verse] = it }
            dto.date?.let { updateStatement[PersonOfDayTable.date] = java.time.LocalDate.parse(it) }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        PersonOfDayTable.deleteWhere { PersonOfDayTable.id eq id } > 0
    }
}