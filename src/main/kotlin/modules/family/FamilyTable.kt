package com.fathersprophets.backend.modules.family

import com.fathersprophets.backend.modules.user.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object FamilyTable : Table("family") {
    val id = integer("id").autoIncrement()
    val familyName = varchar("family_name", 255)
    val image = text("image").nullable()
    val leaderId = integer("leader_id").references(UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_family_user_id")
    val subLeaderId = integer("sub_leader_id").references(UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_family_sub_leader_id")

    override val primaryKey = PrimaryKey(id)
}