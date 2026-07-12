package com.fathersprophets.backend.database.dao.activity.timeline

import com.fathersprophets.backend.database.tables.activity.timeline.TimelineTable
import com.fathersprophets.backend.models.dto.TimelineDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class TimelineDao {

    private fun resultRowToDto(row: ResultRow) = TimelineDto(
        id = row[TimelineTable.id],
        event1 = row[TimelineTable.event1],
        event2 = row[TimelineTable.event2],
        event3 = row[TimelineTable.event3],
        event4 = row[TimelineTable.event4],
        correctOrder = row[TimelineTable.correctOrder]
    )

    fun findAll() = transaction {
        TimelineTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        TimelineTable.selectAll().where { TimelineTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun create(dto: TimelineDto) = transaction {
        TimelineTable.insert {
            it[event1] = dto.event1
            it[event2] = dto.event2
            it[event3] = dto.event3
            it[event4] = dto.event4
            it[correctOrder] = dto.correctOrder
        } get TimelineTable.id
    }

    fun update(dto: TimelineDto) = transaction {
        TimelineTable.update({ TimelineTable.id eq dto.id }) {
            it[event1] = dto.event1
            it[event2] = dto.event2
            it[event3] = dto.event3
            it[event4] = dto.event4
            it[correctOrder] = dto.correctOrder
        } > 0
    }

    fun delete(id: Int) = transaction {
        TimelineTable.deleteWhere { TimelineTable.id eq id } > 0
    }
}