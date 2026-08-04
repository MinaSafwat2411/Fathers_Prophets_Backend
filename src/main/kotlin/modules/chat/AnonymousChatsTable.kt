package com.fathersprophets.backend.modules.chat

import com.fathersprophets.backend.modules.user.UsersTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp


object AnonymousChatsTable : Table("anonymous_chats") {
    val id = integer("id").autoIncrement()
    val memberId = reference("member_id", UsersTable.id).index("idx_anonymous_chats_member_id")
    val servantId = reference("servant_id", UsersTable.id).index("idx_anonymous_chats_servant_id")
    val lastMessage = varchar("last_message", 255).nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(memberId, servantId)
    }
}