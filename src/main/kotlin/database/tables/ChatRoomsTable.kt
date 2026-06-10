package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.postgresql.util.PGobject

enum class ChatRoomType { DIRECT, GROUP }

object ChatRoomsTable : Table("chat_rooms") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255).nullable()
    val type = customEnumeration(
        "type",
        "chat_room_type",
        { value -> ChatRoomType.valueOf(value as String) },
        { PGobject().apply { type = "chat_room_type"; value = it.name } }
    )
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}