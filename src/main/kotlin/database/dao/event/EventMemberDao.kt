package com.fathersprophets.backend.database.dao.event

import com.fathersprophets.backend.database.tables.EventMembersTable
import com.fathersprophets.backend.database.dto.event.EventMemberDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class EventMemberDao {
    private fun rowToEventMember(row: ResultRow) = EventMemberDto(
        id = row[EventMembersTable.id],
        eventId = row[EventMembersTable.eventId],
        userId = row[EventMembersTable.userId],
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
        }.resultedValues?.singleOrNull()?.let { rowToEventMember(it) }
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