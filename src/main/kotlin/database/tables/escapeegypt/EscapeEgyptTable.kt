package com.fathersprophets.backend.database.tables.escapeegypt

import com.fathersprophets.backend.database.enums.EscapeEgyptType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object EscapeEgyptTable : Table("escape_egypt") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 255)
    val type = customEnumeration(
        "type",
        "escape_egypt_type",
        { value -> EscapeEgyptType.valueOf(value as String) },
        { PGobject().apply { type = "escape_egypt_type"; value = it.name.lowercase() } }
    )

    override val primaryKey = PrimaryKey(id)

    init {
        TransactionManager.current().exec(
            """
                DO $$ BEGIN 
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'escape_egypt_type') THEN 
                        CREATE TYPE escape_egypt_type AS ENUM (
                        'From', 'To'
                        ); 
                    END IF; 
                END $$;
            """.trimIndent()
        )
    }
}