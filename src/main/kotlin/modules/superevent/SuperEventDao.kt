package com.fathersprophets.backend.modules.superevent

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class SuperEventDao {

    private fun ResultRow.toDto() = SuperEventDto(
        id = this[SuperEventsTable.id],
        title = this[SuperEventsTable.title],
        description = this[SuperEventsTable.description],
        location = this[SuperEventsTable.location],
        startDate = this[SuperEventsTable.startDate].toString(),
        endDate = this[SuperEventsTable.endDate].toString(),
        lastBookingDate = this[SuperEventsTable.lastBookingDate].toString(),
        totalSeats = this[SuperEventsTable.totalSeats],
        waitingListLimit = this[SuperEventsTable.waitingListLimit],
        image = this[SuperEventsTable.image],
        createdAt = this[SuperEventsTable.createdAt].toString(),
        teachers = this[SuperEventsTable.teachers]
    )

    fun getAll() = transaction {
        SuperEventsTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        SuperEventsTable.selectAll()
            .where { SuperEventsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun create(dto: SuperEventCreateDto) = transaction {
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
            it[teachers] = dto.teachers
        }.let { getById(it[SuperEventsTable.id]) }
    }

    fun update(id: Int, dto: SuperEventUpdateDto) = transaction {
        SuperEventsTable.update({ SuperEventsTable.id eq id }) { updateStatement ->
            dto.title?.let { updateStatement[SuperEventsTable.title] = it }
            dto.description?.let { updateStatement[SuperEventsTable.description] = it }
            dto.location?.let { updateStatement[SuperEventsTable.location] = it }
            dto.startDate?.let { updateStatement[SuperEventsTable.startDate] = LocalDate.parse(it) }
            dto.endDate?.let { updateStatement[SuperEventsTable.endDate] = LocalDate.parse(it) }
            dto.lastBookingDate?.let { updateStatement[SuperEventsTable.lastBookingDate] = LocalDate.parse(it) }
            dto.totalSeats?.let { updateStatement[SuperEventsTable.totalSeats] = it }
            dto.waitingListLimit?.let { updateStatement[SuperEventsTable.waitingListLimit] = it }
            dto.image?.let { updateStatement[SuperEventsTable.image] = it }
            dto.teachers?.let { updateStatement[SuperEventsTable.teachers] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        SuperEventsTable.deleteWhere { SuperEventsTable.id eq id } > 0
    }
}