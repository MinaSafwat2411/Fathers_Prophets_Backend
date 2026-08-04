package com.fathersprophets.backend.modules.person

import com.fathersprophets.backend.database.enums.PersonType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object PersonsTable : Table("persons") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val nickname = varchar("nickname", 255).nullable()
    val shortStory = varchar("short_story", 255).nullable()
    val fullStory = varchar("full_story", 255).nullable()
    val image = text("image").nullable()
    val type = customEnumeration(
        "type",
        "person_type",
        { value -> PersonType.valueOf(value as String) },
        { PGobject().apply { type = "person_type"; value = it.name } }
    ).index("idx_persons_type")

    override val primaryKey = PrimaryKey(id)

    init {
        TransactionManager.current().exec(
            """
                DO $$ BEGIN 
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'person_type') THEN 
                        CREATE TYPE person_type AS ENUM (
                            'Prophets','Fathers','Saints','Apostles','Judges'
                            ); 
                    END IF; 
                END $$;
            """.trimIndent()
        )
    }
}