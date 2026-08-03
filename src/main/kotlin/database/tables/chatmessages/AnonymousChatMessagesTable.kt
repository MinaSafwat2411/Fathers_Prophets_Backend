package com.fathersprophets.backend.database.tables.chatmessages

import com.fathersprophets.backend.database.tables.chat.AnonymousChatsTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp

object AnonymousChatMessagesTable : Table("anonymous_chat_messages") {
    val id = integer("id").autoIncrement()
    val chatId = reference("chat_id", AnonymousChatsTable.id, onDelete = ReferenceOption.CASCADE).index("idx_anonymous_chat_messages_chat_id")
    val message = text("message")
    val isRead = bool("is_read").default(false)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)

    override val primaryKey = PrimaryKey(id)
}