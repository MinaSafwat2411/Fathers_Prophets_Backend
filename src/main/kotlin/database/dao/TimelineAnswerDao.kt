package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.timeline.TimelineAnswersTable
import com.fathersprophets.backend.models.dto.TimelineAnswerDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class TimelineAnswerDao {

    private fun resultRowToDto(row: ResultRow) = TimelineAnswerDto(
        id = row[TimelineAnswersTable.id],
        timelineId = row[TimelineAnswersTable.timelineId],
        userId = row[TimelineAnswersTable.userId],
        status = row[TimelineAnswersTable.status]
    )

    fun findAll() = transaction {
        TimelineAnswersTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        TimelineAnswersTable.selectAll().where { TimelineAnswersTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun findByUserId(userId: Int) = transaction {
        TimelineAnswersTable.selectAll().where { TimelineAnswersTable.userId eq userId }
            .map { resultRowToDto(it) }
    }

    fun create(dto: TimelineAnswerDto) = transaction {
        TimelineAnswersTable.insert {
            it[timelineId] = dto.timelineId
            it[userId] = dto.userId
            it[status] = dto.status
        }.let { findById(it[TimelineAnswersTable.id]) }
    }

    fun delete(id: Int) = transaction {
        TimelineAnswersTable.deleteWhere { TimelineAnswersTable.id eq id } > 0
    }
}