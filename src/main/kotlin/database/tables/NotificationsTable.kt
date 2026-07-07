package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp
import org.postgresql.util.PGobject

object NotificationsTable : Table("notifications") {
    val id = integer("id").autoIncrement()
    val eventId = reference("event_id", EventsTable.id, onDelete = ReferenceOption.CASCADE).index("idx_notifications_event_id")
    val type = customEnumeration(
        "type",
        "event_type",
        { value -> EventType.valueOf(value as String) },
        { PGobject().apply { type = "event_type"; value = it.name } }
    ).index("idx_notifications_type")
    val title = varchar("title", 255)
    val message = text("message").nullable()
    val isRead = bool("is_read").default(false)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)

    override val primaryKey = PrimaryKey(id)
}