package com.fathersprophets.backend.database.tables.attendance

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.timestamp

object SessionTable : Table("sessions") {
    val id = integer("id").autoIncrement()
    val dateTime = datetime("date_time").uniqueIndex()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)

    override val primaryKey = PrimaryKey(id)
}