package com.fathersprophets.backend.modules.event

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class EventDao {

    private fun ResultRow.toDto() = EventDto(
        id = this[EventsTable.id],
        type = this[EventsTable.type],
        title = this[EventsTable.title],
        dateTime = this[EventsTable.dateTime].toString(),
        image = this[EventsTable.image],
        familyId = this[EventsTable.familyId]
    )

    fun getAll() = transaction {
        EventsTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        EventsTable.selectAll()
            .where { EventsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByFamilyId(familyId: Int) = transaction {
        EventsTable.selectAll()
            .where { EventsTable.familyId eq familyId }
            .map { it.toDto() }
    }

    fun create(dto: EventCreateDto) = transaction {
        EventsTable.insert {
            it[type] = dto.type
            it[title] = dto.title
            it[dateTime] = java.time.LocalDate.parse(dto.dateTime)
            it[image] = dto.image
            it[familyId] = dto.familyId
        }.let { getById(it[EventsTable.id]) }
    }

    fun update(id: Int, dto: EventUpdateDto) = transaction {
        EventsTable.update({ EventsTable.id eq id }) { updateStatement ->
            dto.type?.let { updateStatement[EventsTable.type] = it }
            dto.title?.let { updateStatement[EventsTable.title] = it }
            dto.dateTime?.let { updateStatement[EventsTable.dateTime] = java.time.LocalDate.parse(it) }
            dto.image?.let { updateStatement[EventsTable.image] = it }
            dto.familyId?.let { updateStatement[EventsTable.familyId] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        EventsTable.deleteWhere { EventsTable.id eq id } > 0
    }
}