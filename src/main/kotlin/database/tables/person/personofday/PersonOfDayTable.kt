package com.fathersprophets.backend.database.tables.person.personofday

import com.fathersprophets.backend.database.tables.person.PersonsTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object PersonOfDayTable : Table("person_of_day") {
    val id = integer("id").autoIncrement()
    val personId = reference("person_id", PersonsTable.id)
    val message = varchar("message", 255)
    val verse = varchar("verse", 255)
    val date = date("date")

    override val primaryKey = PrimaryKey(id)
}