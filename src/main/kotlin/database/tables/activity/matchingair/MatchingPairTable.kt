package com.fathersprophets.backend.database.tables.activity.matchingair

import com.fathersprophets.backend.database.tables.person.PersonsTable
import org.jetbrains.exposed.sql.Table

object MatchingPairTable : Table("matching_pairs") {
    val id = integer("id").autoIncrement()
    val personId = reference("person_id", PersonsTable.id)
    val personName = varchar("person_name", 255)
    val otherSide = varchar("other_side", 255)

    override val primaryKey = PrimaryKey(id)
}