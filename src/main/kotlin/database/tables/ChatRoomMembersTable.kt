package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.postgresql.util.PGobject

enum class ChatMemberRole { ADMIN, MEMBER }

object ChatRoomMembersTable : Table("chat_room_members") {
    val roomId = reference("room_id", ChatRoomsTable.id)
    val userId = reference("user_id", UsersTable.id)
    val role = customEnumeration(
        "role",
        "chat_member_role",
        { value -> ChatMemberRole.valueOf(value as String) },
        { PGobject().apply { type = "chat_member_role"; value = it.name } }
    ).default(ChatMemberRole.MEMBER)
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(roomId, userId)
}