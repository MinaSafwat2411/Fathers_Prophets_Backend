package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.postgresql.util.PGobject

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
        { PGobject().apply { type = "event_type"; value = it.name } }
    ).index("idx_events_type")
    val title = varchar("title", 255)
    val dateTime = date("date_time").index("idx_events_date_time")
    val image = varchar("image", 255).nullable()

    override val primaryKey = PrimaryKey(id)
}