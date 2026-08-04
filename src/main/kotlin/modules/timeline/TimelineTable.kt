package com.fathersprophets.backend.modules.timeline

import org.jetbrains.exposed.sql.Table

object TimelineTable : Table("timeline") {
    val id = integer("id").autoIncrement()
    val event1 = varchar("event_1", 255)
    val event2 = varchar("event_2", 255)
    val event3 = varchar("event_3", 255)
    val event4 = varchar("event_4", 255)

    val event5 = varchar("event_5", 255).nullable()
    val event6 = varchar("event_6", 255).nullable()
    val event7 = varchar("event_7", 255).nullable()
    val event8 = varchar("event_8", 255).nullable()

    val event9 = varchar("event_9", 255).nullable()
    val event10 = varchar("event_10", 255).nullable()

    val correctOrder = array<Int>("correct_order")

    override val primaryKey = PrimaryKey(id)
}