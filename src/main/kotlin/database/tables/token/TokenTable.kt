package com.fathersprophets.backend.modules.token

import com.fathersprophets.backend.database.tables.user.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object TokenTable : Table("tokens") {
    val id = integer("id").autoIncrement()
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_tokens_user_id")
    val token = text("token").index("idx_tokens_token")
    val refreshToken = text("refresh_token").index("idx_tokens_refresh_token")
    val expiresAt = long("expires_at").index("idx_tokens_expires_at")
    val fcmToken = varchar("fcm_token", 512).nullable()
    val adminToken = varchar("admin_token", 255).nullable()
    override val primaryKey = PrimaryKey(id)
}