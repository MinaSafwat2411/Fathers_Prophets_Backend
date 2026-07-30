package com.fathersprophets.backend.database.tables.attendance

import com.fathersprophets.backend.database.tables.classes.ClassesTable
import com.fathersprophets.backend.database.tables.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object AttendanceTable : Table("attendance") {

    val id = integer("id").autoIncrement()
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_attendance_user_id")
    val sessionId = reference("session_id", SessionTable.id, onDelete = ReferenceOption.CASCADE).index("idx_attendance_session_id")
    val name = varchar("name", 255)
    val attended = bool("attended").default(false)
    val broughtBible = bool("brought_bible").default(false)
    val shmas = bool("shmas").default(false)
    val odas = bool("odas").default(false)
    val tnawl = bool("tnawl").default(false)
    val classId = reference("class_id", ClassesTable.id, onDelete = ReferenceOption.CASCADE).index("idx_attendance_class_id")

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex("attendance_user_session_unique", userId, sessionId)
    }

}