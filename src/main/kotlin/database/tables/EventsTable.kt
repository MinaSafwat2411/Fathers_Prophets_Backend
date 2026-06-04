package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

enum class EventType {
    football,
    volleyball,
    chess,
    pingPong,
    pray,
    praise,
    doctrine,
    bible,
    ritual,
    coptic,
    choir,
    mahrgan,
    odas,
    shmas,
    melodies
}

object EventsTable : Table("events") {
    val id = integer("id").autoIncrement()
    val type = customEnumeration(
        "type",
        "event_type",
        { value -> EventType.valueOf(value as String) },
        { it.name }
    )
    val title = varchar("title", 255)
    val dateTime = date("date_time")
    val image = varchar("image", 255).nullable()

    override val primaryKey = PrimaryKey(id)
}