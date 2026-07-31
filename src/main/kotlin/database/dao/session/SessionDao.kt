package com.fathersprophets.backend.database.dao.session

import com.fathersprophets.backend.database.tables.attendance.SessionTable
import com.fathersprophets.backend.database.dto.sessions.SessionDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
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
        familyId = row[SessionTable.familyId],
        dateTime = row[SessionTable.dateTime].toString(),
        createdAt = row[SessionTable.createdAt].toString()
    )

    fun findById(sessionId: Int) = transaction {
        SessionTable.selectAll().where { SessionTable.id eq sessionId }
            .singleOrNull()?.let { rowToSession(it) }
    }

    fun addSession(session: SessionDto) = transaction {
        SessionTable.insert {
            it[dateTime] = LocalDateTime.parse(session.dateTime)
            it[familyId] = session.familyId
        }.resultedValues?.singleOrNull()?.let { rowToSession(it) }
    }

    fun updateSession(session: SessionDto) = transaction {
        SessionTable.update({ SessionTable.id eq session.id }) {
            it[dateTime] = LocalDateTime.parse(session.dateTime)
            it[familyId] = session.familyId
        }.let { findById(session.id) }
    }

    fun deleteSession(sessionId: Int) = transaction {
        SessionTable.deleteWhere { SessionTable.id eq sessionId } > 0
    }



    fun getAllSessions() = transaction {
        SessionTable.selectAll().orderBy(SessionTable.dateTime, SortOrder.DESC).map { rowToSession(it) }
    }

    fun getSessionsByFamilyId(familyId: Int) = transaction {
        SessionTable.selectAll().where { SessionTable.familyId eq familyId }
            .orderBy(SessionTable.dateTime, SortOrder.DESC).map { rowToSession(it) }
    }
}