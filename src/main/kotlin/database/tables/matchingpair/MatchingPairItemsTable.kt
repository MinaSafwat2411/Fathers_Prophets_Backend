package com.fathersprophets.backend.database.tables.matchingpair

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object MatchingPairItemsTable : Table("pair_items") {
    val id = integer("id").autoIncrement()
    val pairId = reference("pair_id",MatchingPairTable.id, onDelete = ReferenceOption.CASCADE).index("idx_pair_items_pair_id")

    val right = varchar("right", 255)
    val left = varchar("left", 255)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(pairId, right, left)
    }

}