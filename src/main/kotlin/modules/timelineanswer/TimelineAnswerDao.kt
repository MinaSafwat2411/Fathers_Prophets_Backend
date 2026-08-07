package com.fathersprophets.backend.modules.timelineanswer

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class TimelineAnswerDao : CrudDao<TimelineAnswerDto, TimelineAnswerCreateDto, TimelineAnswerUpdateDto> {

    private fun ResultRow.toDto() = TimelineAnswerDto(
        id = this[TimelineAnswersTable.id],
        timelineId = this[TimelineAnswersTable.timelineId],
        userId = this[TimelineAnswersTable.userId],
        order = this[TimelineAnswersTable.order],
        status = this[TimelineAnswersTable.status]
    )

    override fun getAll() = transaction {
        TimelineAnswersTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        TimelineAnswersTable.selectAll()
            .where { TimelineAnswersTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByTimelineId(timelineId: Int) = transaction {
        TimelineAnswersTable.selectAll()
            .where { TimelineAnswersTable.timelineId eq timelineId }
            .map { it.toDto() }
    }

    fun getByUserId(userId: Int) = transaction {
        TimelineAnswersTable.selectAll()
            .where { TimelineAnswersTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByTimelineAndUser(timelineId: Int, userId: Int) = transaction {
        TimelineAnswersTable.selectAll()
            .where { (TimelineAnswersTable.timelineId eq timelineId) and (TimelineAnswersTable.userId eq userId) }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: TimelineAnswerCreateDto) = transaction {
        TimelineAnswersTable.insert {
            it[timelineId] = dto.timelineId
            it[userId] = dto.userId
            it[order] = dto.order
            it[status] = dto.status
        }.let { getById(it[TimelineAnswersTable.id]) }
    }

    override fun update(id: Int, dto: TimelineAnswerUpdateDto) = transaction {
        TimelineAnswersTable.update({ TimelineAnswersTable.id eq id }) { updateStatement ->
            dto.timelineId?.let { updateStatement[TimelineAnswersTable.timelineId] = it }
            dto.userId?.let { updateStatement[TimelineAnswersTable.userId] = it }
            dto.order?.let { updateStatement[TimelineAnswersTable.order] = it }
            dto.status?.let { updateStatement[TimelineAnswersTable.status] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        TimelineAnswersTable.deleteWhere { TimelineAnswersTable.id eq id } > 0
    }
}