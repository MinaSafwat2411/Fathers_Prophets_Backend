package com.fathersprophets.backend.database.tables.notification

import com.fathersprophets.backend.database.tables.users.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object UserNotificationsTable : Table("user_notifications") {
    val id = integer("id").autoIncrement()

    val notificationId = integer("notification_id")
        .references(NotificationsTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_user_notifications_notification_id")

    val userId = integer("user_id")
        .references(UsersTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_user_notifications_user_id")

    val isRead = bool("is_read").default(false).index("idx_user_notifications_is_read")
    val readAt = timestamp("read_at").nullable()

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex("uq_user_notification", userId, notificationId)
    }
}