package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table

object PersonOfDayTable : Table("person_of_day") {
    val id = integer("id").autoIncrement()
    val personId = reference("person_id", PersonsTable.id)
    val message = varchar("message", 255)
    val verse = varchar("verse", 255)

    override val primaryKey = PrimaryKey(id)
}