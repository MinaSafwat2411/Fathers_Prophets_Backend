package com.fathersprophets.backend.database.dao.attendance

import com.fathersprophets.backend.database.tables.attendance.AttendanceTable
import com.fathersprophets.backend.models.dto.AttendanceDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

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

    fun getAllAttendanceByUserId(userId : Int) = transaction {
        AttendanceTable.select { AttendanceTable.userId eq userId }.map { rowToAttendanceDto(it) }
    }

    fun getAllAttendanceByClassId(classId: Int) = transaction {
        AttendanceTable.select { AttendanceTable.classId eq classId }.map { rowToAttendanceDto(it) }
    }

    fun getAllAttendanceBySessionId(sessionId: Int) = transaction {
        AttendanceTable.select { AttendanceTable.sessionId eq sessionId }.map { rowToAttendanceDto(it) }
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
        } get AttendanceTable.id
    }

    fun updateAttendance(attendanceDto: AttendanceDto) = transaction {
        AttendanceTable.update({ AttendanceTable.id eq attendanceDto.id }) {
            it[attended] = attendanceDto.attended
            it[broughtBible] = attendanceDto.broughtBible
            it[shmas] = attendanceDto.shmas
            it[odas] = attendanceDto.odas
            it[tnawl] = attendanceDto.tnawl
            it[classId] = attendanceDto.classId
        } > 0
    }

    fun deleteAttendance(attendanceId: Int) = transaction {
        AttendanceTable.deleteWhere { AttendanceTable.id eq attendanceId } > 0
    }
}