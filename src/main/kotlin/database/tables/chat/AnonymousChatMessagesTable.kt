package com.fathersprophets.backend.database.tables.chat

import com.fathersprophets.backend.database.tables.users.UsersTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object AnonymousChatMessagesTable : Table("anonymous_chat_messages") {
    val id = integer("id").autoIncrement()
    val chatId = reference("chat_id", AnonymousChatsTable.id)
    val memberId = reference("member_id", UsersTable.id)
    val servantId = reference("servant_id", UsersTable.id)
    val message = text("message")
    val servantName = varchar("servant_name", 255).nullable()
    val memberName = varchar("member_name", 255).nullable()
    val isRead = bool("is_read").default(false)
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)
}