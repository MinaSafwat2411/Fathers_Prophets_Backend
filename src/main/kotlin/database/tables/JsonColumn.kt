package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

internal class JsonColumnType : ColumnType<String>() {
    override fun sqlType() = "JSON"
    override fun valueFromDB(value: Any): String = when (value) {
        is PGobject -> value.value ?: "{}"
        else -> value.toString()
    }
    override fun notNullValueToDB(value: String) = PGobject().apply {
        type = "json"
        this.value = value
    }
}

internal fun Table.json(name: String): Column<String> = registerColumn(name, JsonColumnType())