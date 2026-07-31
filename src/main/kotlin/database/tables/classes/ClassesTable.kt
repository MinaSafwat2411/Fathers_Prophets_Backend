package com.fathersprophets.backend.database.tables.classes

import com.fathersprophets.backend.database.tables.family.FamilyTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ClassesTable : Table("class") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val image = text("image").nullable()
    val familyId = integer("family_id").references(FamilyTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_classes_family_id")

    override val primaryKey = PrimaryKey(id)
}