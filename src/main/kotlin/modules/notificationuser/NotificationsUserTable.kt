package com.fathersprophets.backend.modules.notificationuser

import com.fathersprophets.backend.modules.notification.NotificationsTable
import com.fathersprophets.backend.database.tables.user.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object NotificationsUserTable : Table("user_notifications") {
    val id = integer("id").autoIncrement()

    val notificationId = reference("notification_id", NotificationsTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_user_notifications_notification_id")

    val userId = reference("user_id",UsersTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_user_notifications_user_id")

    val isRead = bool("is_read").default(false).index("idx_user_notifications_is_read")
    val readAt = timestamp("read_at").nullable()

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex("uq_user_notification", userId, notificationId)
    }
}