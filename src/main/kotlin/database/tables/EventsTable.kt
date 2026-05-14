package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object EventsTable : Table("events") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val dateTime = date("date_time")
    val image = varchar("image", 255).nullable()

    override val primaryKey = PrimaryKey(id)
}
