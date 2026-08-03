package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.session.AttendanceTable
import com.fathersprophets.backend.database.dto.session.AttendanceDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class AttendanceDao {
    private fun rowToAttendanceDto(raw: ResultRow) = AttendanceDto(
        id = raw[AttendanceTable.id],
        userId = raw[AttendanceTable.userId],
        sessionId = raw[AttendanceTable.sessionId],
        name = raw[AttendanceTable.name],
        attended = raw[AttendanceTable.attended],
        broughtBible = raw[AttendanceTable.broughtBible],
        shmas = raw[AttendanceTable.shmas],
        odas = raw[AttendanceTable.odas],
        tnawl = raw[AttendanceTable.tnawl],
        classId = raw[AttendanceTable.classId]
    )

    fun getAllAttendance() = transaction {
        AttendanceTable.selectAll().map { rowToAttendanceDto(it) }
    }

    fun findById(attendanceId: Int) = transaction {
        AttendanceTable.selectAll().where { AttendanceTable.id eq attendanceId }
            .singleOrNull()?.let { rowToAttendanceDto(it) }
    }

    fun getAllAttendanceByUserId(userId : Int) = transaction {
        AttendanceTable.selectAll().where { AttendanceTable.userId eq userId }.map { rowToAttendanceDto(it) }
    }

    fun getAllAttendanceBySessionId(sessionId: Int) = transaction {
        AttendanceTable.selectAll().where { AttendanceTable.sessionId eq sessionId }.map { rowToAttendanceDto(it) }
    }

    fun getAllAttendanceByClassIdAndSessionId(classId: Int, sessionId: Int) = transaction {
        AttendanceTable.selectAll()
            .where { AttendanceTable.classId eq classId and (AttendanceTable.sessionId eq sessionId) }.map { rowToAttendanceDto(it) }
    }

    fun addAttendance(attendanceDto: AttendanceDto) = transaction {
        AttendanceTable.insert {
            it[userId] = attendanceDto.userId
            it[sessionId] = attendanceDto.sessionId
            it[name] = attendanceDto.name
            it[attended] = attendanceDto.attended
            it[broughtBible] = attendanceDto.broughtBible
            it[shmas] = attendanceDto.shmas
            it[odas] = attendanceDto.odas
            it[tnawl] = attendanceDto.tnawl
            it[classId] = attendanceDto.classId
        }.resultedValues?.singleOrNull()?.let { rowToAttendanceDto(it) }
    }

    fun updateAttendance(attendanceDto: AttendanceDto) = transaction {
        AttendanceTable.update({ AttendanceTable.id eq attendanceDto.id }) {
            it[attended] = attendanceDto.attended
            it[broughtBible] = attendanceDto.broughtBible
            it[shmas] = attendanceDto.shmas
            it[odas] = attendanceDto.odas
            it[tnawl] = attendanceDto.tnawl
        }.let { findById(attendanceDto.id) }
    }

    fun deleteAttendance(attendanceId: Int) = transaction {
        AttendanceTable.deleteWhere { AttendanceTable.id eq attendanceId } > 0
    }
}