package com.fathersprophets.backend.database.dao.attendance

import com.fathersprophets.backend.database.tables.attendance.SessionTable
import com.fathersprophets.backend.models.dto.SessionDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime

class SessionDao {
    private fun rowToSession(row: ResultRow) = SessionDto(
        id = row[SessionTable.id],
        dateTime = row[SessionTable.dateTime].toString(),
        createdAt = row[SessionTable.createdAt].toString()
    )

    fun addSession(session: SessionDto) = transaction {
        SessionTable.insert {
            it[dateTime] = LocalDateTime.parse(session.dateTime)
        } get SessionTable.id
    }

    fun updateSession(session: SessionDto) = transaction {
        SessionTable.update({ SessionTable.id eq session.id }) {
            it[dateTime] = LocalDateTime.parse(session.dateTime)
        } > 0
    }

    fun deleteSession(sessionId: Int) = transaction {
        SessionTable.deleteWhere { SessionTable.id eq sessionId } > 0
    }

    fun getSessionById(sessionId: Int) = transaction {
        SessionTable.selectAll().where { SessionTable.id eq sessionId }
            .singleOrNull()?.let { rowToSession(it) }
    }

    fun getAllSessions() = transaction {
        SessionTable.selectAll().map { rowToSession(it) }
    }
}