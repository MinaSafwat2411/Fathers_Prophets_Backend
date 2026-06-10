package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object AnonymousChatMessagesTable : Table("anonymous_chat_messages") {
    val id = integer("id").autoIncrement()
    val chatId = reference("chat_id", AnonymousChatsTable.id)
    val senderId = reference("sender_id", UsersTable.id)
    val message = text("message")
    val isRead = bool("is_read").default(false)
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)
}