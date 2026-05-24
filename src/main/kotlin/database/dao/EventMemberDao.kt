package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.EventMembersTable
import com.fathersprophets.backend.models.dto.EventMemberDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class EventMemberDao {
    private fun rowToEventMember(row: ResultRow) = EventMemberDto(
        id = row[EventMembersTable.id],
        eventId = row[EventMembersTable.eventId],
        userId = row[EventMembersTable.userId],
        name = row[EventMembersTable.name],
        eventType = row[EventMembersTable.eventType]
    )

    fun addEventMember(eventMemberDto: EventMemberDto) = transaction{
        EventMembersTable.insert {
            it[eventId] = eventMemberDto.eventId
            it[userId] = eventMemberDto.userId
            it[name] = eventMemberDto.name
            it[eventType] = eventMemberDto.eventType
        } get EventMembersTable.id
    }

    fun getEventMemberByIdAndEventId(eventMemberDto: EventMemberDto) = transaction {
        EventMembersTable.select {
            (EventMembersTable.eventId eq eventMemberDto.eventId) and
                    (EventMembersTable.userId eq eventMemberDto.userId)
        }.map { rowToEventMember(it) }.first()
    }

    fun deleteEventMember(eventMemberDto: EventMemberDto) = transaction {
        EventMembersTable.deleteWhere {
            (EventMembersTable.eventId eq eventMemberDto.eventId) and
            (EventMembersTable.userId eq eventMemberDto.userId)
        }
    }

    fun getEventMembersByEventId(eventMemberDto: EventMemberDto) = transaction {
        EventMembersTable.select { EventMembersTable.eventId eq eventMemberDto.eventId }
            .map { rowToEventMember(it) }
    }

    fun getEventMembersByUserId(eventMemberDto: EventMemberDto) = transaction{
        EventMembersTable.select { EventMembersTable.userId eq eventMemberDto.userId }
            .map { rowToEventMember(it) }
    }
}