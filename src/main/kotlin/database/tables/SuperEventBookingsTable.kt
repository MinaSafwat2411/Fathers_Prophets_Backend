package com.fathersprophets.backend.database.tables

import com.fathersprophets.backend.database.enums.SuperEventBookingStatus
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp
import org.postgresql.util.PGobject

object SuperEventBookingsTable : Table("super_event_bookings") {
    val id = integer("id").autoIncrement()
    val superEventId = reference("super_event_id", SuperEventsTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_super_event_bookings_event_id")
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_super_event_bookings_user_id")
    val name = varchar("name", 255)
    val totalPaid = integer("total_paid").default(0)
    val status = customEnumeration(
        "status",
        "super_event_booking_status",
        { value -> SuperEventBookingStatus.valueOf(value as String) },
        { PGobject().apply { type = "super_event_booking_status"; value = it.name.lowercase() } }
    ).index("idx_super_event_bookings_status")
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val teacherId = reference("teacher_id", UsersTable.id, onDelete = ReferenceOption.SET_NULL)
        .nullable()
        .index("idx_super_event_bookings_teacher_id")

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex("super_event_bookings_event_user_unique", superEventId, userId)
    }
}