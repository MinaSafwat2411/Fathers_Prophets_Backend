package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object CommentsTable : Table("users_comments") {
    val id = integer("id").autoIncrement()
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_users_comments_user_id")
    val comment = varchar("comment", 255)

    override val primaryKey = PrimaryKey(id)
}
