package com.fathersprophets.backend.modules.personofday

import com.fathersprophets.backend.modules.person.PersonsTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object PersonOfDayTable : Table("person_of_day") {
    val id = integer("id").autoIncrement()
    val personId = reference("person_id", PersonsTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_person_of_day_person_id")
    val message = varchar("message", 255)
    val verse = varchar("verse", 255)
    val date = date("date")

    override val primaryKey = PrimaryKey(id)
}