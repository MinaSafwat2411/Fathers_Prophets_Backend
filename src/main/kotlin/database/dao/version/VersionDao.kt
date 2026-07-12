package com.fathersprophets.backend.database.dao.version

import com.fathersprophets.backend.database.tables.vesion.VersionsTable
import com.fathersprophets.backend.models.dto.VersionDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class VersionDao {
    private fun resultRowToVersion(row: ResultRow) = VersionDto(
        id = row[VersionsTable.id],
        version = row[VersionsTable.version],
        adminPin = row[VersionsTable.adminPin]
    )

    fun getLastVersion() = transaction {
        VersionsTable.selectAll().lastOrNull()?.let { resultRowToVersion(it) }
    }

    fun getPinByVersion(versionDto: VersionDto) = transaction {
        VersionsTable.selectAll().where { VersionsTable.version eq versionDto.version }
            .singleOrNull()?.let { resultRowToVersion(it) }
    }

    fun addVersion(versionDto: VersionDto) = transaction {
        VersionsTable.insert {
            it[version] = versionDto.version
            it[adminPin] = versionDto.adminPin
        } get VersionsTable.id
    }

    fun changePinVersion(versionDto: VersionDto) = transaction {
        VersionsTable.update({ VersionsTable.version eq (versionDto.version ?: "") }) {
            it[adminPin] = versionDto.adminPin
        } > 0
    }

}