package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object EventMembersTable : Table("event_members") {
    val id = integer("id").autoIncrement()
    val eventId = integer("event_id").references(EventsTable.id, onDelete = ReferenceOption.CASCADE)
    val userId = integer("user_id").references(UsersTable.id, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val eventType = varchar("event_type", 255).check("event_members_type_check") {
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

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex("event_members_event_user_unique", eventId, userId)
    }
}
