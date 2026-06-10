package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object ChatMessagesTable : Table("chat_messages") {
    val id = integer("id").autoIncrement()
    val roomId = reference("room_id", ChatRoomsTable.id)
    val senderId = reference("sender_id", UsersTable.id)
    val message = text("message")
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)
}