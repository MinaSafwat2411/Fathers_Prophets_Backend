package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.SuperEventsTable
import com.fathersprophets.backend.models.dto.SuperEventDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.LocalDate

class SuperEventDao {

    private fun rowToDto(row: ResultRow) = SuperEventDto(
        id = row[SuperEventsTable.id],
        title = row[SuperEventsTable.title],
        description = row[SuperEventsTable.description],
        location = row[SuperEventsTable.location],
        startDate = row[SuperEventsTable.startDate].toString(),
        endDate = row[SuperEventsTable.endDate].toString(),
        lastBookingDate = row[SuperEventsTable.lastBookingDate].toString(),
        totalSeats = row[SuperEventsTable.totalSeats],
        waitingListLimit = row[SuperEventsTable.waitingListLimit],
        image = row[SuperEventsTable.image],
        createdAt = row[SuperEventsTable.createdAt].toString()
    )

    fun findAll() = transaction {
        SuperEventsTable.selectAll()
            .orderBy(SuperEventsTable.startDate)
            .map { rowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        SuperEventsTable.selectAll().where { SuperEventsTable.id eq id }
            .singleOrNull()?.let { rowToDto(it) }
    }

    fun findUpcoming() = transaction {
        SuperEventsTable.selectAll().where { SuperEventsTable.endDate greaterEq LocalDate.now() }
            .orderBy(SuperEventsTable.startDate)
            .map { rowToDto(it) }
    }

    fun create(dto: SuperEventDto) = transaction {
        SuperEventsTable.insert {
            it[title] = dto.title
            it[description] = dto.description
            it[location] = dto.location
            it[startDate] = LocalDate.parse(dto.startDate)
            it[endDate] = LocalDate.parse(dto.endDate)
            it[lastBookingDate] = LocalDate.parse(dto.lastBookingDate)
            it[totalSeats] = dto.totalSeats
            it[waitingListLimit] = dto.waitingListLimit
            it[image] = dto.image
        } get SuperEventsTable.id
    }

    fun update(dto: SuperEventDto) = transaction {
        SuperEventsTable.update({ SuperEventsTable.id eq dto.id }) {
            it[title] = dto.title
            it[description] = dto.description
            it[location] = dto.location
            it[startDate] = LocalDate.parse(dto.startDate)
            it[endDate] = LocalDate.parse(dto.endDate)
            it[lastBookingDate] = LocalDate.parse(dto.lastBookingDate)
            it[totalSeats] = dto.totalSeats
            it[waitingListLimit] = dto.waitingListLimit
            it[image] = dto.image
        } > 0
    }

    fun delete(id: Int) = transaction {
        SuperEventsTable.deleteWhere { SuperEventsTable.id eq id } > 0
    }
}