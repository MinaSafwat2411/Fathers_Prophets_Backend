package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table

object ClassMemberTable : Table("class_members") {
    val id = integer("id").autoIncrement()
    val classId = integer("class_id").references(ClassesTable.id)
    val userId = integer("user_id").references(UsersTable.id)
    val teacher = bool("teacher").default(false)
    val image = varchar("image", 255).nullable()

    val  name = varchar("name", 255).nullable()

    override val primaryKey = PrimaryKey(id)
    init {
        uniqueIndex(classId, userId)
    }
}