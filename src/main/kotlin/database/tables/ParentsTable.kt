package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table

object ParentsTable : Table("parents") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UsersTable.id)
    val motherPhone = varchar("mother_phone", 255).nullable()
    val fatherPhone = varchar("father_phone", 255).nullable()

    override val primaryKey = PrimaryKey(id)
}