package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

enum class EscapeEgyptType { from, to }

object EscapeEgyptTable : Table("escape_egypt") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 255)
    val type = customEnumeration(
        "type",
        "escape_egypt_type",
        { value -> EscapeEgyptType.valueOf(value as String) },
        { PGobject().apply { type = "escape_egypt_type"; value = it.name } }
    )

    override val primaryKey = PrimaryKey(id)
}