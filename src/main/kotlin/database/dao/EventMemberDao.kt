package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.event.EventMembersTable
import com.fathersprophets.backend.models.dto.EventMemberDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class EventMemberDao {
    private fun rowToEventMember(row: ResultRow) = EventMemberDto(
        id = row[EventMembersTable.id],
        eventId = row[EventMembersTable.eventId],
        userId = row[EventMembersTable.userId],
        name = row[EventMembersTable.name],
        eventType = row[EventMembersTable.eventType]
    )

    fun findAll() = transaction {
        EventMembersTable.selectAll().map { rowToEventMember(it) }
    }


    fun findById(eventMemberId : Int) = transaction {
        EventMembersTable.selectAll().where(EventMembersTable.id eq eventMemberId)
            .map { rowToEventMember(it) }.singleOrNull()
    }
    fun addEventMember(eventMemberDto: EventMemberDto) = transaction {
        EventMembersTable.insert {
            it[eventId] = eventMemberDto.eventId
            it[userId] = eventMemberDto.userId
            it[name] = eventMemberDto.name
            it[eventType] = eventMemberDto.eventType
        }.let { findById(it[EventMembersTable.id]) }
    }

    /** Inserts the whole list in one transaction, so a single bad row rolls the batch back. */
    fun addEventMembersBulk(eventMemberDtos: List<EventMemberDto>) = transaction {
        val ids = EventMembersTable.batchInsert(eventMemberDtos) { dto ->
            this[EventMembersTable.eventId] = dto.eventId
            this[EventMembersTable.userId] = dto.userId
            this[EventMembersTable.name] = dto.name
            this[EventMembersTable.eventType] = dto.eventType
        }.map { it[EventMembersTable.id] }

        EventMembersTable.selectAll().where { EventMembersTable.id inList ids }
            .map { rowToEventMember(it) }
    }


    fun deleteEventMember(eventMemberId : Int) = transaction {
        EventMembersTable.deleteWhere {
            EventMembersTable.id eq eventMemberId
        } > 0
    }

    fun getEventMembersByEventId(eventId: Int) = transaction {
        EventMembersTable.selectAll().where { EventMembersTable.eventId eq eventId }
            .map { rowToEventMember(it) }
    }

    fun getEventMembersByUserId(userId: Int) = transaction {
        EventMembersTable.selectAll().where { EventMembersTable.userId eq userId }
            .map { rowToEventMember(it) }
    }
}