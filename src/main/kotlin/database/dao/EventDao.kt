package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.dto.EventDto
import com.fathersprophets.backend.database.enums.EventType
import com.fathersprophets.backend.database.tables.event.EventsTable
import com.fathersprophets.backend.database.dto.EventCountsDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class EventDao {
    private fun rowToEvent(row: ResultRow) = EventDto(
        id = row[EventsTable.id],
        type = row[EventsTable.type],
        dateTime = row[EventsTable.dateTime],
        title = row[EventsTable.title],
        image = row[EventsTable.image] ?: ""
    )

    fun getAllEvents() = transaction {
        EventsTable.selectAll().map { rowToEvent(it) }
    }

    fun getEventById(eventId: Int) = transaction {
        EventsTable.selectAll().where { EventsTable.id eq eventId }
            .singleOrNull()?.let { rowToEvent(it) }
    }

    fun getEventByType(eventType: EventType) = transaction {
        EventsTable.selectAll().where { EventsTable.type eq eventType }
            .map { rowToEvent(it) }
    }

    fun getUpcomingEvents() = transaction {
        EventsTable.selectAll().where { EventsTable.dateTime greaterEq LocalDate.now() }
            .orderBy(EventsTable.dateTime)
            .map { rowToEvent(it) }
    }

    fun addEvent(eventDto: EventDto) = transaction {
        EventsTable.insert {
            it[title] = eventDto.title
            it[type] = eventDto.type
            it[dateTime] = eventDto.dateTime
            it[image] = eventDto.image
        }.resultedValues?.singleOrNull()?.let { rowToEvent(it) }
    }

    fun updateEvent(eventDto: EventDto) = transaction {
        EventsTable.update({ EventsTable.id eq eventDto.id }) {
            it[title] = eventDto.title
            it[dateTime] = eventDto.dateTime
            it[image] = eventDto.image
            it[type] = eventDto.type
        }.let { getEventById(eventDto.id) }
    }

    fun deleteEvent(eventId : Int) = transaction {
        EventsTable.deleteWhere { EventsTable.id eq eventId } > 0
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