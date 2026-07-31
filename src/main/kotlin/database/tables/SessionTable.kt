package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.timestamp

object SessionTable : Table("sessions") {
    val id = integer("id").autoIncrement()
    val dateTime = datetime("date_time").uniqueIndex()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val familyId = integer("family_id").references(FamilyTable.id, onDelete = ReferenceOption.CASCADE).index("idx_sessions_family_id")

    override val primaryKey = PrimaryKey(id)
}