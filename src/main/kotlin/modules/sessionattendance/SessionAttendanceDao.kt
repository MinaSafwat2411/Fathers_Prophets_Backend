package com.fathersprophets.backend.modules.sessionattendance

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class SessionAttendanceDao : CrudDao<SessionAttendanceDto, SessionAttendanceCreateDto, SessionAttendanceUpdateDto> {

    private fun ResultRow.toDto() = SessionAttendanceDto(
        id = this[SessionAttendanceTable.id],
        userId = this[SessionAttendanceTable.userId],
        sessionId = this[SessionAttendanceTable.sessionId],
        attended = this[SessionAttendanceTable.attended],
        broughtBible = this[SessionAttendanceTable.broughtBible],
        shmas = this[SessionAttendanceTable.shmas],
        odas = this[SessionAttendanceTable.odas],
        tnawl = this[SessionAttendanceTable.tnawl],
        classId = this[SessionAttendanceTable.classId]
    )

    override fun getAll() = transaction {
        SessionAttendanceTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        SessionAttendanceTable.selectAll()
            .where { SessionAttendanceTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getBySessionId(sessionId: Int) = transaction {
        SessionAttendanceTable.selectAll()
            .where { SessionAttendanceTable.sessionId eq sessionId }
            .map { it.toDto() }
    }

    fun getByUserId(userId: Int) = transaction {
        SessionAttendanceTable.selectAll()
            .where { SessionAttendanceTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByUserAndSession(userId: Int, sessionId: Int) = transaction {
        SessionAttendanceTable.selectAll()
            .where { (SessionAttendanceTable.userId eq userId) and (SessionAttendanceTable.sessionId eq sessionId) }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: SessionAttendanceCreateDto) = transaction {
        SessionAttendanceTable.insert {
            it[userId] = dto.userId
            it[sessionId] = dto.sessionId
            it[attended] = dto.attended
            it[broughtBible] = dto.broughtBible
            it[shmas] = dto.shmas
            it[odas] = dto.odas
            it[tnawl] = dto.tnawl
            it[classId] = dto.classId
        }.let { getById(it[SessionAttendanceTable.id]) }
    }

    override fun update(id: Int, dto: SessionAttendanceUpdateDto) = transaction {
        SessionAttendanceTable.update({ SessionAttendanceTable.id eq id }) { updateStatement ->
            dto.userId?.let { updateStatement[SessionAttendanceTable.userId] = it }
            dto.sessionId?.let { updateStatement[SessionAttendanceTable.sessionId] = it }
            dto.attended?.let { updateStatement[SessionAttendanceTable.attended] = it }
            dto.broughtBible?.let { updateStatement[SessionAttendanceTable.broughtBible] = it }
            dto.shmas?.let { updateStatement[SessionAttendanceTable.shmas] = it }
            dto.odas?.let { updateStatement[SessionAttendanceTable.odas] = it }
            dto.tnawl?.let { updateStatement[SessionAttendanceTable.tnawl] = it }
            dto.classId?.let { updateStatement[SessionAttendanceTable.classId] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        SessionAttendanceTable.deleteWhere { SessionAttendanceTable.id eq id } > 0
    }
}