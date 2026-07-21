package com.fathersprophets.backend.database.tables.classes

import com.fathersprophets.backend.database.tables.users.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ClassMemberTable : Table("class_members") {
    val id = integer("id").autoIncrement()
    val classId = reference("class_id", ClassesTable.id, onDelete = ReferenceOption.CASCADE).index("idx_class_members_class_id")
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_class_members_user_id")
    val teacher = bool("teacher").default(false)
    val image = text("image").nullable()
    val name = varchar("name", 255)

    override val primaryKey = PrimaryKey(id)
    init {
        uniqueIndex(classId, userId)
    }
}