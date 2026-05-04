package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table

object VersionsTable : Table("versions") {
    val id = integer("id").autoIncrement()
    val version = varchar("version", 255)
    val adminPin = varchar("admin_pin", 255)

    override val primaryKey = PrimaryKey(id)
}
