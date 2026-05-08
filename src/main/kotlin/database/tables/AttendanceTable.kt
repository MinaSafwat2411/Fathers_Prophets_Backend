package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table

object AttendanceTable : Table("attendance") {

    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UsersTable.id)
    val sessionId = integer("session_id").references(SessionTable.id)
    val name = varchar("name", 255)
    val attended = bool("attended").default(false)
    val broughtBible = bool("brought_bible").default(false)
    val shmas = bool("shmas").default(false)
    val odas = bool("odas").default(false)
    val tnawl = bool("tnawl").default(false)
    val classId = integer("class_id").references(ClassesTable.id)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex("attendance_user_session_unique", userId, sessionId)
    }

}