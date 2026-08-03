package com.fathersprophets.backend.database.tables.matchingpair

import org.jetbrains.exposed.sql.Table

object MatchingPairTable : Table("matching_pairs") {
    val id = integer("id").autoIncrement()

    val title = varchar("title", 255)

    override val primaryKey = PrimaryKey(id)
}