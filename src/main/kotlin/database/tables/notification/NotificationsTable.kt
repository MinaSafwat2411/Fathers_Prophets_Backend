package com.fathersprophets.backend.database.tables.notification

import com.fathersprophets.backend.database.enums.NotificationType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object NotificationsTable : Table("notifications") {
    val id = integer("id").autoIncrement()
    val type = customEnumeration(
        "type",
        "notification_type",
        { value -> NotificationType.entries.first { it.name.equals((value as String), ignoreCase = true) } },
        { PGobject().apply { type = "notification_type"; value = it.name.lowercase() } }
    ).index("idx_notifications_type")

    val title = varchar("title", 255)
    val description = text("description")
    val referenceId = integer("reference_id").nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)

    override val primaryKey = PrimaryKey(id)

    init {
        TransactionManager.current().exec(
            """
            DO $$ BEGIN 
                IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'notification_type') THEN 
                    CREATE TYPE notification_type AS ENUM (
                        'event', 'birthday', 'superevent', 'chat'
                    ); 
                END IF; 
            END $$;
            """.trimIndent()
        )
    }
}