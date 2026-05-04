package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.VersionsTable
import com.fathersprophets.backend.models.dto.version.AdminPin
import com.fathersprophets.backend.models.dto.version.AdminPinRequest
import com.fathersprophets.backend.models.dto.version.VersionRequest
import com.fathersprophets.backend.models.dto.version.VersionResponse
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class VersionDao {
    private fun resultRowToVersion(row: ResultRow) = VersionResponse(
        id = row[VersionsTable.id],
        version = row[VersionsTable.version],
    )

    private fun resultRowToAdmin(row: ResultRow) = AdminPin(
        id = row[VersionsTable.id],
        version = row[VersionsTable.version],
        adminPin = row[VersionsTable.adminPin]
    )

    fun getLastVersion() = transaction {
        VersionsTable.selectAll().lastOrNull()?.let { resultRowToVersion(it) }
    }

    fun getPinByVersion(version: String) = transaction {
        VersionsTable.selectAll().where { VersionsTable.version eq version }
            .singleOrNull()?.let { resultRowToAdmin(it) }
    }

    fun addVersion(versionRequest: VersionRequest) = transaction {
        VersionsTable.insert {
            it[version] = versionRequest.version
            it[adminPin] = versionRequest.adminPin
        } get VersionsTable.id
    }

    fun changePinVersion(adminPinRequest: AdminPinRequest) = transaction {
        VersionsTable.update({ VersionsTable.version eq adminPinRequest.version }) {
            it[adminPin] = adminPinRequest.adminPin
        }
    }

}