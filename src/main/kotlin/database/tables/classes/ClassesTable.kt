package com.fathersprophets.backend.database.tables.classes
import org.jetbrains.exposed.sql.Table

object ClassesTable : Table("classes") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val image = text("image").nullable()

    override val primaryKey = PrimaryKey(id)
}