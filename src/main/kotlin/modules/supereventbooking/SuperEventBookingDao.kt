package com.fathersprophets.backend.modules.supereventbooking

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class SuperEventBookingDao {

    private fun ResultRow.toDto() = SuperEventBookingDto(
        id = this[SuperEventBookingsTable.id],
        superEventId = this[SuperEventBookingsTable.superEventId],
        userId = this[SuperEventBookingsTable.userId],
        name = this[SuperEventBookingsTable.name],
        totalPaid = this[SuperEventBookingsTable.totalPaid],
        status = this[SuperEventBookingsTable.status],
        createdAt = this[SuperEventBookingsTable.createdAt].toString(),
        teacherId = this[SuperEventBookingsTable.teacherId]
    )

    fun getAll() = transaction {
        SuperEventBookingsTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        SuperEventBookingsTable.selectAll()
            .where { SuperEventBookingsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getBySuperEventId(superEventId: Int) = transaction {
        SuperEventBookingsTable.selectAll()
            .where { SuperEventBookingsTable.superEventId eq superEventId }
            .map { it.toDto() }
    }

    fun getByUserId(userId: Int) = transaction {
        SuperEventBookingsTable.selectAll()
            .where { SuperEventBookingsTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getBySuperEventAndUser(superEventId: Int, userId: Int) = transaction {
        SuperEventBookingsTable.selectAll()
            .where { (SuperEventBookingsTable.superEventId eq superEventId) and (SuperEventBookingsTable.userId eq userId) }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun create(dto: SuperEventBookingCreateDto) = transaction {
        SuperEventBookingsTable.insert {
            it[superEventId] = dto.superEventId
            it[userId] = dto.userId
            it[name] = dto.name
            it[totalPaid] = dto.totalPaid
            it[status] = dto.status
            it[teacherId] = dto.teacherId
        }.let { getById(it[SuperEventBookingsTable.id]) }
    }

    fun update(id: Int, dto: SuperEventBookingUpdateDto) = transaction {
        SuperEventBookingsTable.update({ SuperEventBookingsTable.id eq id }) { updateStatement ->
            dto.superEventId?.let { updateStatement[SuperEventBookingsTable.superEventId] = it }
            dto.userId?.let { updateStatement[SuperEventBookingsTable.userId] = it }
            dto.name?.let { updateStatement[SuperEventBookingsTable.name] = it }
            dto.totalPaid?.let { updateStatement[SuperEventBookingsTable.totalPaid] = it }
            dto.status?.let { updateStatement[SuperEventBookingsTable.status] = it }
            dto.teacherId?.let { updateStatement[SuperEventBookingsTable.teacherId] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        SuperEventBookingsTable.deleteWhere { SuperEventBookingsTable.id eq id } > 0
    }
}