package com.fathersprophets.backend.modules.otp

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant

class OtpDao : CrudDao<OtpDto, OtpCreateDto, OtpUpdateDto> {

    private fun ResultRow.toDto() = OtpDto(
        id = this[OtpTable.id],
        userId = this[OtpTable.userId],
        type = this[OtpTable.type],
        otpCode = this[OtpTable.otpCode],
        otpExpiresAt = this[OtpTable.otpExpiresAt]?.toString(),
        pendingValue = this[OtpTable.pendingValue],
        resetTransactionId = this[OtpTable.resetTransactionId],
        resetVerifyToken = this[OtpTable.resetVerifyToken],
        resetVerifyTokenExpiresAt = this[OtpTable.resetVerifyTokenExpiresAt]?.toString()
    )

    override fun getAll() = transaction {
        OtpTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        OtpTable.selectAll()
            .where { OtpTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByUserId(userId: Int) = transaction {
        OtpTable.selectAll()
            .where { OtpTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByUserAndType(userId: Int, type: com.fathersprophets.backend.database.enums.OtpType) = transaction {
        OtpTable.selectAll()
            .where { (OtpTable.userId eq userId) and (OtpTable.type eq type) }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByResetTransactionId(resetTransactionId: String) = transaction {
        OtpTable.selectAll()
            .where { OtpTable.resetTransactionId eq resetTransactionId }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByResetVerifyToken(resetVerifyToken: String) = transaction {
        OtpTable.selectAll()
            .where { OtpTable.resetVerifyToken eq resetVerifyToken }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: OtpCreateDto) = transaction {
        OtpTable.insert {
            it[userId] = dto.userId
            it[type] = dto.type
            it[otpCode] = dto.otpCode
            it[otpExpiresAt] = dto.otpExpiresAt?.let { instantStr -> Instant.parse(instantStr) }
            it[pendingValue] = dto.pendingValue
            it[resetTransactionId] = dto.resetTransactionId
            it[resetVerifyToken] = dto.resetVerifyToken
            it[resetVerifyTokenExpiresAt] = dto.resetVerifyTokenExpiresAt?.let { instantStr -> Instant.parse(instantStr) }
        }.let { getById(it[OtpTable.id]) }
    }

    override fun update(id: Int, dto: OtpUpdateDto) = transaction {
        OtpTable.update({ OtpTable.id eq id }) { updateStatement ->
            dto.userId?.let { updateStatement[OtpTable.userId] = it }
            dto.type?.let { updateStatement[OtpTable.type] = it }
            dto.otpCode?.let { updateStatement[OtpTable.otpCode] = it }
            dto.otpExpiresAt?.let { updateStatement[OtpTable.otpExpiresAt] = Instant.parse(it) }
            dto.pendingValue?.let { updateStatement[OtpTable.pendingValue] = it }
            dto.resetTransactionId?.let { updateStatement[OtpTable.resetTransactionId] = it }
            dto.resetVerifyToken?.let { updateStatement[OtpTable.resetVerifyToken] = it }
            dto.resetVerifyTokenExpiresAt?.let { updateStatement[OtpTable.resetVerifyTokenExpiresAt] = Instant.parse(it) }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        OtpTable.deleteWhere { OtpTable.id eq id } > 0
    }
}