package com.fathersprophets.backend.modules.timeline

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class TimelineDao {

    private fun ResultRow.toDto() = TimelineDto(
        id = this[TimelineTable.id],
        event1 = this[TimelineTable.event1],
        event2 = this[TimelineTable.event2],
        event3 = this[TimelineTable.event3],
        event4 = this[TimelineTable.event4],
        event5 = this[TimelineTable.event5],
        event6 = this[TimelineTable.event6],
        event7 = this[TimelineTable.event7],
        event8 = this[TimelineTable.event8],
        event9 = this[TimelineTable.event9],
        event10 = this[TimelineTable.event10],
        correctOrder = this[TimelineTable.correctOrder]
    )

    fun getAll() = transaction {
        TimelineTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        TimelineTable.selectAll()
            .where { TimelineTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun create(dto: TimelineCreateDto) = transaction {
        TimelineTable.insert {
            it[event1] = dto.event1
            it[event2] = dto.event2
            it[event3] = dto.event3
            it[event4] = dto.event4
            it[event5] = dto.event5
            it[event6] = dto.event6
            it[event7] = dto.event7
            it[event8] = dto.event8
            it[event9] = dto.event9
            it[event10] = dto.event10
            it[correctOrder] = dto.correctOrder
        }.let { getById(it[TimelineTable.id]) }
    }

    fun update(id: Int, dto: TimelineUpdateDto) = transaction {
        TimelineTable.update({ TimelineTable.id eq id }) { updateStatement ->
            dto.event1?.let { updateStatement[TimelineTable.event1] = it }
            dto.event2?.let { updateStatement[TimelineTable.event2] = it }
            dto.event3?.let { updateStatement[TimelineTable.event3] = it }
            dto.event4?.let { updateStatement[TimelineTable.event4] = it }
            dto.event5?.let { updateStatement[TimelineTable.event5] = it }
            dto.event6?.let { updateStatement[TimelineTable.event6] = it }
            dto.event7?.let { updateStatement[TimelineTable.event7] = it }
            dto.event8?.let { updateStatement[TimelineTable.event8] = it }
            dto.event9?.let { updateStatement[TimelineTable.event9] = it }
            dto.event10?.let { updateStatement[TimelineTable.event10] = it }
            dto.correctOrder?.let { updateStatement[TimelineTable.correctOrder] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        TimelineTable.deleteWhere { TimelineTable.id eq id } > 0
    }
}