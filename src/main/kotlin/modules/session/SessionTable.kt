package com.fathersprophets.backend.modules.session

import com.fathersprophets.backend.database.tables.family.FamilyTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.timestamp

object SessionTable : Table("sessions") {
    val id = integer("id").autoIncrement()
    val dateTime = datetime("date_time").uniqueIndex()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val familyId = reference("family_id", FamilyTable.id, onDelete = ReferenceOption.CASCADE).index("idx_sessions_family_id")

    override val primaryKey = PrimaryKey(id)
}