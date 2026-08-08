package com.fathersprophets.backend.modules.chat

import com.fathersprophets.backend.database.tables.user.UsersTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp


object AnonymousChatsTable : Table("anonymous_chats") {
    val id = integer("id").autoIncrement()

    val sender = reference("sender_id", UsersTable.id).index("idx_anonymous_chats_sender_id")
    val receiver = reference("receiver_id", UsersTable.id).index("idx_anonymous_chats_receiver_id")
    val isBlocked = bool("is_blocked").default(false)

    val lastMessage = varchar("last_message", 255).nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(sender,receiver)
    }
}