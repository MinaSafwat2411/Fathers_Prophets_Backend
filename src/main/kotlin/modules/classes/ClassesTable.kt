package com.fathersprophets.backend.modules.classes

import com.fathersprophets.backend.modules.family.FamilyTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ClassesTable : Table("class") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val image = text("image").nullable()
    val familyId = reference("family_id", FamilyTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_classes_family_id")

    override val primaryKey = PrimaryKey(id)
}