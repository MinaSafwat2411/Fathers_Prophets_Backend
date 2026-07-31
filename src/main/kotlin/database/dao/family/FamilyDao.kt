package com.fathersprophets.backend.database.dao.family


import com.fathersprophets.backend.database.dto.family.FamilyDto
import com.fathersprophets.backend.database.tables.family.FamilyTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class FamilyDao {

    private fun resultRowToFamily(row: ResultRow) = FamilyDto(
        id = row[FamilyTable.id],
        familyName = row[FamilyTable.familyName],
        image = row[FamilyTable.image],
        leaderId = row[FamilyTable.leaderId],
        subLeaderId = row[FamilyTable.subLeaderId]
    )

    fun getAllFamilies(): List<FamilyDto> = transaction {
        FamilyTable.selectAll().map(::resultRowToFamily)
    }

    fun getFamilyById(familyId: Int) = transaction {
        FamilyTable.selectAll()
            .where { FamilyTable.id eq familyId }
            .map(::resultRowToFamily)
            .singleOrNull()
    }

    fun createFamily(dto: FamilyDto) = transaction {
        FamilyTable.insert {
            it[familyName] = dto.familyName
            it[image] = dto.image
            it[leaderId] = dto.leaderId
            it[subLeaderId] = dto.subLeaderId
        }.resultedValues?.singleOrNull()?.let { it[FamilyTable.id] }
    }

    fun updateFamily(dto: FamilyDto) = transaction {
       FamilyTable.update({ FamilyTable.id eq dto.id }) {
            it[familyName] = dto.familyName
            it[image] = dto.image
            it[leaderId] = dto.leaderId
            it[subLeaderId] = dto.subLeaderId
        }.let { getFamilyById(dto.id) }
    }

    fun deleteFamily(familyId: Int) = transaction {
        FamilyTable.deleteWhere { FamilyTable.id eq familyId } > 0
    }
}