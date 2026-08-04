package com.fathersprophets.backend.modules.family


import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class FamilyDao {

    private fun ResultRow.toDto() = FamilyDto(
        id = this[FamilyTable.id],
        familyName = this[FamilyTable.familyName],
        image = this[FamilyTable.image],
        leaderId = this[FamilyTable.leaderId],
        subLeaderId = this[FamilyTable.subLeaderId]
    )

    fun getAll() = transaction {
        FamilyTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        FamilyTable.selectAll()
            .where { FamilyTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByLeaderId(leaderId: Int) = transaction {
        FamilyTable.selectAll()
            .where { FamilyTable.leaderId eq leaderId }
            .map { it.toDto() }
    }

    fun getBySubLeaderId(subLeaderId: Int) = transaction {
        FamilyTable.selectAll()
            .where { FamilyTable.subLeaderId eq subLeaderId }
            .map { it.toDto() }
    }

    fun create(dto: FamilyCreateDto) = transaction {
        FamilyTable.insert {
            it[familyName] = dto.familyName
            it[image] = dto.image
            it[leaderId] = dto.leaderId
            it[subLeaderId] = dto.subLeaderId
        }.let { getById(it[FamilyTable.id]) }
    }

    fun update(id: Int, dto: FamilyUpdateDto) = transaction {
        FamilyTable.update({ FamilyTable.id eq id }) { updateStatement ->
            dto.familyName?.let { updateStatement[FamilyTable.familyName] = it }
            dto.image?.let { updateStatement[FamilyTable.image] = it }
            dto.leaderId?.let { updateStatement[FamilyTable.leaderId] = it }
            dto.subLeaderId?.let { updateStatement[FamilyTable.subLeaderId] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        FamilyTable.deleteWhere { FamilyTable.id eq id } > 0
    }
}