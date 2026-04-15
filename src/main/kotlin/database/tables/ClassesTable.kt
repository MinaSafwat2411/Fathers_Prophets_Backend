package com.fathersprophets.backend.database.tables
import org.jetbrains.exposed.sql.Table

object ClassesTable : Table("classes") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val image = varchar("image", 255).nullable()

    override val primaryKey = PrimaryKey(id)
}