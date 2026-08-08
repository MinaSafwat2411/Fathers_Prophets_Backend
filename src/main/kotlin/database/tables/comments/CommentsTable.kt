package com.fathersprophets.backend.database.tables.comments

import com.fathersprophets.backend.database.tables.user.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object CommentsTable : Table("users_comments") {
    val id = integer("id").autoIncrement()
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_users_comments_user_id")
    val comment = varchar("comment", 255)
    val teacherId = reference("teacher_id",UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_users_comments_teacher_id")

    override val primaryKey = PrimaryKey(id)
}