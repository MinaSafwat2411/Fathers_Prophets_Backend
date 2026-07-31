package com.fathersprophets.backend.database.tables

import com.fathersprophets.backend.database.enums.EventType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object EventsTable : Table("events") {
    val id = integer("id").autoIncrement()
    val type = customEnumeration(
        "type",
        "event_type",
        { value -> EventType.entries.first { it.name.equals((value as String), ignoreCase = true) } },
        { PGobject().apply { type = "event_type"; value = it.name.lowercase() } }
    ).index("idx_events_type")
    val title = varchar("title", 255)
    val dateTime = date("date_time").index("idx_events_date_time")
    val image = text("image").nullable()

    override val primaryKey = PrimaryKey(id)

    init {
        TransactionManager.current().exec(
            """
            DO $$ BEGIN 
                IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'event_type') THEN 
                    CREATE TYPE event_type AS ENUM (
                        'football', 'volleyball', 'chess', 'pingpong', 'pray', 
                        'praise', 'doctrine', 'bible', 'ritual', 'coptic', 
                        'choir', 'carnival', 'odas', 'deacon', 'melodies'
                    ); 
                END IF; 
            END $$;
            """.trimIndent()
        )
    }
}