package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.timestamp

object SuperEventsTable : Table("super_events") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 255)
    val description = text("description").nullable()
    val location = varchar("location", 255).nullable()
    val startDate = date("start_date").index("idx_super_events_start_date")
    val endDate = date("end_date")
    val lastBookingDate = date("last_booking_date").index("idx_super_events_last_booking_date")
    val totalSeats = integer("total_seats")
    val waitingListLimit = integer("waiting_list_limit").default(0)
    val image = text("image").nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val teachers = json("teachers").default("[]")

    override val primaryKey = PrimaryKey(id)
}