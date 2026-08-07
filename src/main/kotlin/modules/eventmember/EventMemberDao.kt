package com.fathersprophets.backend.modules.eventmember

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class EventMemberDao : CrudDao<EventMemberDto, EventMemberCreateDto, EventMemberUpdateDto> {

    private fun ResultRow.toDto() = EventMemberDto(
        id = this[EventMembersTable.id],
        eventId = this[EventMembersTable.eventId],
        userId = this[EventMembersTable.userId]
    )

    override fun getAll() = transaction {
        EventMembersTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        EventMembersTable.selectAll()
            .where { EventMembersTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByEventId(eventId: Int) = transaction {
        EventMembersTable.selectAll()
            .where { EventMembersTable.eventId eq eventId }
            .map { it.toDto() }
    }

    fun getByUserId(userId: Int) = transaction {
        EventMembersTable.selectAll()
            .where { EventMembersTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByEventAndUser(eventId: Int, userId: Int) = transaction {
        EventMembersTable.selectAll()
            .where { (EventMembersTable.eventId eq eventId) and (EventMembersTable.userId eq userId) }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: EventMemberCreateDto) = transaction {
        EventMembersTable.insert {
            it[eventId] = dto.eventId
            it[userId] = dto.userId
        }.let { getById(it[EventMembersTable.id]) }
    }

    override fun update(id: Int, dto: EventMemberUpdateDto) = transaction {
        EventMembersTable.update({ EventMembersTable.id eq id }) { updateStatement ->
            dto.eventId?.let { updateStatement[EventMembersTable.eventId] = it }
            dto.userId?.let { updateStatement[EventMembersTable.userId] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        EventMembersTable.deleteWhere { EventMembersTable.id eq id } > 0
    }
}