package com.fathersprophets.backend.database.tables.auth

import com.fathersprophets.backend.database.tables.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object TokenTable : Table("tokens") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id")
        .references(UsersTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_tokens_user_id")
    val token = text("token").index("idx_tokens_token")
    val refreshToken = text("refresh_token").index("idx_tokens_refresh_token")
    val expiresAt = long("expires_at").index("idx_tokens_expires_at")
    val fcmToken = varchar("fcm_token", 512).nullable()
    override val primaryKey = PrimaryKey(id)
}