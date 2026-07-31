package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table

object PairItemsTable : Table("pair_items") {
    val id = integer("id").autoIncrement()
    val pairId = integer("pair_id").references(MatchingPairTable.id).index("idx_pair_items_pair_id")

    val right = varchar("right", 255)
    val left = varchar("left", 255)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(pairId, right, left)
    }

}