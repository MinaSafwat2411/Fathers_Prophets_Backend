package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object EventsTable : Table("events") {
    val id = integer("id").autoIncrement()
    val type = varchar("type", 255).check("events_type_check") {
        it inList listOf(
            "football",
            "volleyball",
            "chess",
            "pingPong",
            "pray",
            "praise",
            "doctrine",
            "bible",
            "ritual",
            "coptic",
            "choir",
            "mahrgan",
            "odas",
            "shmas",
            "melodies"
        )
    }
    val title = varchar("title", 255)
    val dateTime = date("date_time")
    val image = varchar("image", 255).nullable()

    override val primaryKey = PrimaryKey(id)
}