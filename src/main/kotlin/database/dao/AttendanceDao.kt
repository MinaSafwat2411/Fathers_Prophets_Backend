package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.AttendanceTable
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

    fun getAllAttendanceByUserId(attendanceDto: AttendanceDto) = transaction {
        AttendanceTable.select { AttendanceTable.userId eq attendanceDto.userId }.map { rowToAttendanceDto(it) }
    }

    fun getAllAttendanceByClassId(attendanceDto: AttendanceDto) = transaction {
        AttendanceTable.select { AttendanceTable.classId eq attendanceDto.classId }.map { rowToAttendanceDto(it) }
    }

    fun getAllAttendanceBySessionId(attendanceDto: AttendanceDto) = transaction {
        AttendanceTable.select { AttendanceTable.sessionId eq attendanceDto.sessionId }.map { rowToAttendanceDto(it) }
    }

    fun getAttendanceById(attendanceDto: AttendanceDto) = transaction {
        AttendanceTable.select { AttendanceTable.id eq attendanceDto.id }.map { rowToAttendanceDto(it) }.singleOrNull()
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
        }
    }

    fun deleteAttendance(attendanceDto: AttendanceDto) =transaction {
        AttendanceTable.deleteWhere { AttendanceTable.id eq attendanceDto.id }
    }
}
