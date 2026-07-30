package com.fathersprophets.backend.database.tables.chat

import com.fathersprophets.backend.database.tables.UsersTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp


object AnonymousChatsTable : Table("anonymous_chats") {
    val id = integer("id").autoIncrement()
    val memberId = reference("member_id", UsersTable.id)
    val servantId = reference("servant_id", UsersTable.id)
    val lastMessage = varchar("last_message", 255).nullable()
    val servantName = varchar("servant_name", 255).nullable()
    val memberName = varchar("member_name", 255).nullable()
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(memberId, servantId)
    }
}