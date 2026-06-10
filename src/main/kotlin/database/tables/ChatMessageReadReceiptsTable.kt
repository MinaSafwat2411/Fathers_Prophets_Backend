package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object ChatMessageReadReceiptsTable : Table("chat_message_read_receipts") {
    val messageId = reference("message_id", ChatMessagesTable.id)
    val userId = reference("user_id", UsersTable.id)
    val readAt = timestamp("read_at")

    override val primaryKey = PrimaryKey(messageId, userId)
}