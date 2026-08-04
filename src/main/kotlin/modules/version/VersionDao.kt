package com.fathersprophets.backend.modules.version

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class VersionDao {

    private fun ResultRow.toDto() = VersionDto(
        id = this[VersionsTable.id],
        version = this[VersionsTable.version],
        versionCode = this[VersionsTable.versionCode],
        adminPin = this[VersionsTable.adminPin]
    )

    fun getAll() = transaction {
        VersionsTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        VersionsTable.selectAll()
            .where { VersionsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByVersion(version: String) = transaction {
        VersionsTable.selectAll()
            .where { VersionsTable.version eq version }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByVersionCode(versionCode: Int) = transaction {
        VersionsTable.selectAll()
            .where { VersionsTable.versionCode eq versionCode }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getLatest() = transaction {
        VersionsTable.selectAll()
            .orderBy(VersionsTable.versionCode, SortOrder.DESC)
            .limit(1)
            .map { it.toDto() }
            .singleOrNull()
    }

    fun create(dto: VersionCreateDto) = transaction {
        VersionsTable.insert {
            it[version] = dto.version
            it[versionCode] = dto.versionCode
            it[adminPin] = dto.adminPin
        }.let { getById(it[VersionsTable.id]) }
    }

    fun update(id: Int, dto: VersionUpdateDto) = transaction {
        VersionsTable.update({ VersionsTable.id eq id }) { updateStatement ->
            dto.version?.let { updateStatement[VersionsTable.version] = it }
            dto.versionCode?.let { updateStatement[VersionsTable.versionCode] = it }
            dto.adminPin?.let { updateStatement[VersionsTable.adminPin] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        VersionsTable.deleteWhere { VersionsTable.id eq id } > 0
    }
}