package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.SuperEventBookingStatus
import com.fathersprophets.backend.database.tables.SuperEventBookingsTable
import com.fathersprophets.backend.models.dto.SuperEventBookingDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
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

    fun findById(id: Int) = transaction {
        SuperEventBookingsTable.selectAll().where { SuperEventBookingsTable.id eq id }
            .singleOrNull()?.let { rowToDto(it) }
    }

    fun findByEventAndUser(superEventId: Int, userId: Int) = transaction {
        SuperEventBookingsTable.selectAll().where {
            (SuperEventBookingsTable.superEventId eq superEventId) and
                (SuperEventBookingsTable.userId eq userId)
        }.singleOrNull()?.let { rowToDto(it) }
    }

    fun findByEventId(superEventId: Int) = transaction {
        SuperEventBookingsTable.selectAll().where { SuperEventBookingsTable.superEventId eq superEventId }
            .orderBy(SuperEventBookingsTable.createdAt, SortOrder.ASC)
            .map { rowToDto(it) }
    }

    fun findByUserId(userId: Int) = transaction {
        SuperEventBookingsTable.selectAll().where { SuperEventBookingsTable.userId eq userId }
            .orderBy(SuperEventBookingsTable.createdAt, SortOrder.DESC)
            .map { rowToDto(it) }
    }

    fun countByStatus(superEventId: Int, status: SuperEventBookingStatus) = transaction {
        SuperEventBookingsTable.selectAll().where {
            (SuperEventBookingsTable.superEventId eq superEventId) and
                (SuperEventBookingsTable.status eq status)
        }.count().toInt()
    }

    fun findOldestWaiting(superEventId: Int) = transaction {
        SuperEventBookingsTable.selectAll().where {
            (SuperEventBookingsTable.superEventId eq superEventId) and
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
            it[teacherId] = dto.teacherId
        } get SuperEventBookingsTable.id
    }

    fun updateStatus(id: Int, status: SuperEventBookingStatus) = transaction {
        SuperEventBookingsTable.update({ SuperEventBookingsTable.id eq id }) {
            it[SuperEventBookingsTable.status] = status
        } > 0
    }

    fun updateTotalPaid(id: Int, totalPaid: Int) = transaction {
        SuperEventBookingsTable.update({ SuperEventBookingsTable.id eq id }) {
            it[SuperEventBookingsTable.totalPaid] = totalPaid
        } > 0
    }
}