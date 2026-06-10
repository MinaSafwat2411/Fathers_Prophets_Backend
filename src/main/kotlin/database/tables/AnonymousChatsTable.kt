package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.postgresql.util.PGobject

enum class AnonymousChatStatus { OPEN, CLOSED }

object AnonymousChatsTable : Table("anonymous_chats") {
    val id = integer("id").autoIncrement()
    val memberId = reference("member_id", UsersTable.id)
    val teacherId = reference("teacher_id", UsersTable.id)
    val subject = varchar("subject", 255)
    val status = customEnumeration(
        "status",
        "anonymous_chat_status",
        { value -> AnonymousChatStatus.valueOf(value as String) },
        { PGobject().apply { type = "anonymous_chat_status"; value = it.name } }
    ).default(AnonymousChatStatus.OPEN)
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}