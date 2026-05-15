package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.EventsTable
import com.fathersprophets.backend.models.dto.EventDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.LocalDate

class EventDao {
    private fun rowToEvent(row: ResultRow) = EventDto(
        id = row[EventsTable.id],
        name = row[EventsTable.name],
        dateTime = row[EventsTable.dateTime].toString(),
        image = row[EventsTable.image] ?: ""
    )

    fun getAllEvents() = transaction{
        EventsTable.selectAll().map { rowToEvent(it) }
    }

    fun getEventById(eventId: Int) = transaction {
        EventsTable.selectAll().where { EventsTable.id eq eventId }
            .singleOrNull()?.let { rowToEvent(it) }
    }

    fun getEventByName(eventDto: EventDto) = transaction{
        EventsTable.selectAll().where { EventsTable.name eq eventDto.name }
    }

    fun addEvent(eventDto: EventDto) = transaction{
        EventsTable.insert {
            it[name] = eventDto.name
            it[dateTime] = LocalDate.parse(eventDto.dateTime)
            it[image] = eventDto.image
        }
    }

    fun updateEvent(eventDto: EventDto) = transaction {
        EventsTable.update({ EventsTable.id eq eventDto.id }) {
            it[name] = eventDto.name
            it[dateTime] = LocalDate.parse(eventDto.dateTime)
            it[image] = eventDto.image
        }
    }

    fun deleteEvent(eventDto: EventDto) = transaction {
        EventsTable.deleteWhere { EventsTable.id eq eventDto.id }
    }
}
