package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object EventMembersTable : Table("event_members") {
    val id = integer("id").autoIncrement()
    val eventId = reference("event_id", EventsTable.id, onDelete = ReferenceOption.CASCADE).index("idx_event_members_event_id")
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_event_members_user_id")

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex("event_members_event_user_unique", eventId, userId)
    }
}