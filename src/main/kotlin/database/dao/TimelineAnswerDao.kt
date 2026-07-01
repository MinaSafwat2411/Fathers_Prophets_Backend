package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.TimelineAnswersTable
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

    fun findByTimelineId(timelineId: Int) = transaction {
        TimelineAnswersTable.selectAll().where { TimelineAnswersTable.timelineId eq timelineId }
            .map { resultRowToDto(it) }
    }

    fun findByUserId(userId: Int) = transaction {
        TimelineAnswersTable.selectAll().where { TimelineAnswersTable.userId eq userId }
            .map { resultRowToDto(it) }
    }

    fun findByTimelineIdAndUserId(timelineId: Int, userId: Int) = transaction {
        TimelineAnswersTable.selectAll()
            .where { (TimelineAnswersTable.timelineId eq timelineId) and (TimelineAnswersTable.userId eq userId) }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun create(dto: TimelineAnswerDto) = transaction {
        TimelineAnswersTable.insert {
            it[timelineId] = dto.timelineId
            it[userId] = dto.userId
            it[status] = dto.status
        } get TimelineAnswersTable.id
    }

    fun update(dto: TimelineAnswerDto) = transaction {
        TimelineAnswersTable.update({ TimelineAnswersTable.id eq dto.id }) {
            it[timelineId] = dto.timelineId
            it[userId] = dto.userId
            it[status] = dto.status
        } > 0
    }

    fun updateStatus(dto: TimelineAnswerDto) = transaction {
        TimelineAnswersTable.update({ TimelineAnswersTable.id eq dto.id }) {
            it[status] = dto.status
        } > 0
    }

    fun delete(dto: TimelineAnswerDto) = transaction {
        TimelineAnswersTable.deleteWhere { TimelineAnswersTable.id eq dto.id } > 0
    }
}