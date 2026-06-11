package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table

object TimelineTable : Table("timeline") {
    val id = integer("id").autoIncrement()
    val event1 = varchar("event_1", 255)
    val event2 = varchar("event_2", 255)
    val event3 = varchar("event_3", 255)
    val event4 = varchar("event_4", 255)
    val correctOrder = array<Int>("correct_order")

    override val primaryKey = PrimaryKey(id)
}