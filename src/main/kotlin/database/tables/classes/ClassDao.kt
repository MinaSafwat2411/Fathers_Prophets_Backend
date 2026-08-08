package com.fathersprophets.backend.database.tables.classes

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ClassDao : CrudDao<ClassDto, ClassCreateDto, ClassUpdateDto> {

    private fun ResultRow.toDto() = ClassDto(
        id = this[ClassesTable.id],
        name = this[ClassesTable.name],
        image = this[ClassesTable.image],
        familyId = this[ClassesTable.familyId]
    )

    override fun getAll() = transaction {
        ClassesTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        ClassesTable.selectAll()
            .where { ClassesTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByFamilyId(familyId: Int) = transaction {
        ClassesTable.selectAll()
            .where { ClassesTable.familyId eq familyId }
            .map { it.toDto() }
    }

    override fun create(dto: ClassCreateDto) = transaction {
        ClassesTable.insert {
            it[name] = dto.name
            it[image] = dto.image
            it[familyId] = dto.familyId
        }.let { getById(it[ClassesTable.id]) }
    }

    override fun update(id: Int, dto: ClassUpdateDto) = transaction {
        ClassesTable.update({ ClassesTable.id eq id }) { updateStatement ->
            dto.name?.let { updateStatement[ClassesTable.name] = it }
            dto.image?.let { updateStatement[ClassesTable.image] = it }
            dto.familyId?.let { updateStatement[ClassesTable.familyId] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        ClassesTable.deleteWhere { ClassesTable.id eq id } > 0
    }
}