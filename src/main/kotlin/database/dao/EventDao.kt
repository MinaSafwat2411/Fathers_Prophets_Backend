package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.EventsTable
import com.fathersprophets.backend.models.dto.EventCountsDto
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
        type = row[EventsTable.type],
        dateTime = row[EventsTable.dateTime].toString(),
        title = row[EventsTable.title],
        image = row[EventsTable.image] ?: ""
    )

    fun getAllEvents() = transaction{
        EventsTable.selectAll().map { rowToEvent(it) }
    }

    fun getEventById(eventDto: EventDto) = transaction {
        EventsTable.selectAll().where { EventsTable.id eq eventDto.id }
            .singleOrNull()?.let { rowToEvent(it) }
    }

    fun getEventByType(eventDto: EventDto) = transaction{
        EventsTable.selectAll().where { EventsTable.type eq eventDto.type }
    }

    fun addEvent(eventDto: EventDto) = transaction{
        EventsTable.insert {
            it[title] = eventDto.title
            it[type] = eventDto.type
            it[dateTime] = LocalDate.parse(eventDto.dateTime)
            it[image] = eventDto.image
        } get EventsTable.id
    }

    fun updateEvent(eventDto: EventDto) = transaction {
        EventsTable.update({ EventsTable.id eq eventDto.id }) {
            it[title] = eventDto.title
            it[dateTime] = LocalDate.parse(eventDto.dateTime)
            it[image] = eventDto.image
            it[type] = eventDto.type
        }
    }

    fun deleteEvent(eventDto: EventDto) = transaction {
        EventsTable.deleteWhere { EventsTable.id eq eventDto.id }
    }

    fun getEventsCount() = transaction {
        EventCountsDto(
            total = EventsTable.selectAll().count().toInt(),
            football = EventsTable.selectAll().count().toInt(),
            volleyball = EventsTable.selectAll().count().toInt(),
            chess = EventsTable.selectAll().count().toInt(),
            pingPong = EventsTable.selectAll().count().toInt(),
            pray = EventsTable.selectAll().count().toInt(),
            praise = EventsTable.selectAll().count().toInt(),
            doctrine = EventsTable.selectAll().count().toInt(),
            bible = EventsTable.selectAll().count().toInt(),
            ritual = EventsTable.selectAll().count().toInt(),
            coptic = EventsTable.selectAll().count().toInt(),
            choir = EventsTable.selectAll().count().toInt(),
            mahrgan = EventsTable.selectAll().count().toInt(),
            odas = EventsTable.selectAll().count().toInt(),
            shmas = EventsTable.selectAll().count().toInt(),
            melodies = EventsTable.selectAll().count().toInt()
        )
    }
}
