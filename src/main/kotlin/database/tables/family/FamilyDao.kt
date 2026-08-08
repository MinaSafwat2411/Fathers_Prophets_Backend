package com.fathersprophets.backend.database.tables.family

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class FamilyDao : CrudDao<FamilyDto, FamilyCreateDto, FamilyUpdateDto> {

    private fun ResultRow.toDto() = FamilyDto(
        id = this[FamilyTable.id],
        familyName = this[FamilyTable.familyName],
        image = this[FamilyTable.image],
        leaderId = this[FamilyTable.leaderId],
        subLeaderId = this[FamilyTable.subLeaderId]
    )

    override fun getAll() = transaction {
        FamilyTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        FamilyTable.selectAll()
            .where { FamilyTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: FamilyCreateDto) = transaction {
        FamilyTable.insert {
            it[familyName] = dto.familyName
            it[image] = dto.image
            it[leaderId] = dto.leaderId
            it[subLeaderId] = dto.subLeaderId
        }.let { getById(it[FamilyTable.id]) }
    }

    override fun update(id: Int, dto: FamilyUpdateDto) = transaction {
        FamilyTable.update({ FamilyTable.id eq id }) { updateStatement ->
            dto.familyName?.let { updateStatement[FamilyTable.familyName] = it }
            dto.image?.let { updateStatement[FamilyTable.image] = it }
            dto.leaderId?.let { updateStatement[FamilyTable.leaderId] = it }
            dto.subLeaderId?.let { updateStatement[FamilyTable.subLeaderId] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        FamilyTable.deleteWhere { FamilyTable.id eq id } > 0
    }
}