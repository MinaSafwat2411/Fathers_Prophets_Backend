package com.fathersprophets.backend.modules.personstory

import com.fathersprophets.backend.modules.person.PersonsTable
import org.jetbrains.exposed.sql.Table

object PersonStoryTable : Table("persons_stories") {
    val id = integer("id").autoIncrement()
    val personId = reference("person_id", PersonsTable.id)
    val title = varchar("title", 255)
    val content = text("content")
    val image = varchar("image", 255).nullable()
    val video = varchar("video", 255).nullable()

    override val primaryKey = PrimaryKey(id)
}