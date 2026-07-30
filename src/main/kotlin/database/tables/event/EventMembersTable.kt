package com.fathersprophets.backend.database.tables.event

import com.fathersprophets.backend.database.tables.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

object EventMembersTable : Table("event_members") {
    val id = integer("id").autoIncrement()
    val eventId = reference("event_id", EventsTable.id, onDelete = ReferenceOption.CASCADE).index("idx_event_members_event_id")
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_event_members_user_id")
    val name = varchar("name", 255)
    val eventType = customEnumeration(
        "event_type",
        "event_type",
        { value -> EventType.valueOf(value as String) },
        { PGobject().apply { type = "event_type"; value = it.name } }
    ).index("idx_event_members_event_type")

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex("event_members_event_user_unique", eventId, userId)
    }
}