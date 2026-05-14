package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object EventMembersTable : Table("event_members") {
    val id = integer("id").autoIncrement()
    val eventId = integer("event_id").references(EventsTable.id, onDelete = ReferenceOption.CASCADE)
    val userId = integer("user_id").references(UsersTable.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex("event_members_event_user_unique", eventId, userId)
    }
}
