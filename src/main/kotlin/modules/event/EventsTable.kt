package com.fathersprophets.backend.modules.event

import com.fathersprophets.backend.database.enums.EventType
import com.fathersprophets.backend.modules.family.FamilyTable
import org.jetbrains.exposed.sql.ReferenceOption
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
        { PGobject().apply { type = "event_type"; value = it.name } }
    ).index("idx_events_type")
    val title = varchar("title", 255)
    val dateTime = date("date_time").index("idx_events_date_time")
    val image = text("image").nullable()
    val familyId = reference("family_id", FamilyTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_events_family_id")

    override val primaryKey = PrimaryKey(id)

    init {
        TransactionManager.current().exec(
            """
            DO $$ BEGIN 
                IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'event_type') THEN 
                    CREATE TYPE event_type AS ENUM (
                        'Football', 'Volleyball', 'Chess', 'PingPong', 'Pray', 
                        'Praise', 'Doctrine', 'Bible', 'Ritual', 'Coptic', 
                        'Choir', 'Carnival', 'Odas', 'Deacon', 'Melodies'
                    ); 
                END IF; 
            END $$;
            """.trimIndent()
        )
    }
}