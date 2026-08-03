package com.fathersprophets.backend.database.tables.version

import org.jetbrains.exposed.sql.Table

object VersionsTable : Table("versions") {
    val id = integer("id").autoIncrement()
    val version = varchar("version", 255).uniqueIndex()
    val versionCode = integer("version_code").uniqueIndex()
    val adminPin = varchar("admin_pin", 255)

    override val primaryKey = PrimaryKey(id)
}