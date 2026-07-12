package com.fathersprophets.backend.database.dao.superevent

import com.fathersprophets.backend.database.tables.superevent.SuperEventBookingStatus
import com.fathersprophets.backend.database.tables.superevent.SuperEventBookingsTable
import com.fathersprophets.backend.models.dto.SuperEventBookingDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class SuperEventBookingDao {

    private fun rowToDto(row: ResultRow) = SuperEventBookingDto(
        id = row[SuperEventBookingsTable.id],
        superEventId = row[SuperEventBookingsTable.superEventId],
        userId = row[SuperEventBookingsTable.userId],
        name = row[SuperEventBookingsTable.name],
        totalPaid = row[SuperEventBookingsTable.totalPaid],
        status = row[SuperEventBookingsTable.status],
        createdAt = row[SuperEventBookingsTable.createdAt].toString(),
        teacherId = row[SuperEventBookingsTable.teacherId]
    )
    fun findByEventAndUser(dto : SuperEventBookingDto) = transaction {
        SuperEventBookingsTable.selectAll().where {
            (SuperEventBookingsTable.superEventId eq dto.superEventId) and (SuperEventBookingsTable.userId eq dto.userId)
        }.singleOrNull()?.let { rowToDto(it) }
    }

    fun findById(dto : SuperEventBookingDto) = transaction {
        SuperEventBookingsTable.selectAll().where { SuperEventBookingsTable.id eq dto.id }
            .singleOrNull()?.let { rowToDto(it) }
    }

    fun findByEventId(dto : SuperEventBookingDto) = transaction {
        SuperEventBookingsTable.selectAll().where { SuperEventBookingsTable.superEventId eq dto.superEventId }
            .orderBy(SuperEventBookingsTable.createdAt, SortOrder.ASC)
            .map { rowToDto(it) }
    }

    fun countByStatus(dto : SuperEventBookingDto) = transaction {
        SuperEventBookingsTable.selectAll().where {
            (SuperEventBookingsTable.superEventId eq dto.superEventId) and
                (SuperEventBookingsTable.status eq dto.status)
        }.count().toInt()
    }

    fun findOldestWaiting(dto : SuperEventBookingDto) = transaction {
        SuperEventBookingsTable.selectAll().where {
            (SuperEventBookingsTable.superEventId eq dto.superEventId) and
                (SuperEventBookingsTable.status eq SuperEventBookingStatus.waiting)
        }.orderBy(SuperEventBookingsTable.createdAt, SortOrder.ASC)
            .limit(1)
            .singleOrNull()?.let { rowToDto(it) }
    }

    fun create(dto: SuperEventBookingDto) = transaction {
        SuperEventBookingsTable.insert {
            it[superEventId] = dto.superEventId
            it[userId] = dto.userId
            it[name] = dto.name
            it[status] = dto.status
        } get SuperEventBookingsTable.id
    }

    fun updateStatus(dto : SuperEventBookingDto) = transaction {
        SuperEventBookingsTable.update({ SuperEventBookingsTable.id eq dto.id }) {
            it[SuperEventBookingsTable.status] = dto.status
        } > 0
    }

    fun updateTotalPaid(dto : SuperEventBookingDto) = transaction {
        SuperEventBookingsTable.update({ SuperEventBookingsTable.id eq dto.id }) {
            it[SuperEventBookingsTable.totalPaid] = dto.totalPaid
            it[SuperEventBookingsTable.teacherId] = dto.teacherId
        } > 0
    }
}