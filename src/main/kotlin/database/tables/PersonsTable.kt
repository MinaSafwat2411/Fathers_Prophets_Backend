package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table

enum class PersonType {
    prophets,
    fathers,
    saints,
    apostles,
    judges
}

object PersonsTable : Table("persons") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val nickname = varchar("nickname", 255).nullable()
    val shortStory = varchar("short_story", 255).nullable()
    val fullStory = varchar("full_story", 255).nullable()
    val image = varchar("image", 255).nullable()
    val type = customEnumeration(
        "type",
        "person_type",
        { value -> PersonType.valueOf(value as String) },
        { it.name }
    )

    override val primaryKey = PrimaryKey(id)
}