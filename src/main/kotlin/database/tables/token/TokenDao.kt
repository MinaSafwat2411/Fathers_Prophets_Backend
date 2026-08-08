package com.fathersprophets.backend.modules.token

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class TokenDao : CrudDao<TokenDto, TokenCreateDto, TokenUpdateDto> {

    private fun ResultRow.toDto() = TokenDto(
        id = this[TokenTable.id],
        userId = this[TokenTable.userId],
        token = this[TokenTable.token],
        refreshToken = this[TokenTable.refreshToken],
        expiresAt = this[TokenTable.expiresAt],
        fcmToken = this[TokenTable.fcmToken],
        adminToken = this[TokenTable.adminToken]
    )

    override fun getAll() = transaction {
        TokenTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        TokenTable.selectAll()
            .where { TokenTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByUserId(userId: Int) = transaction {
        TokenTable.selectAll()
            .where { TokenTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getAllFcmTokens() = transaction {
        TokenTable.selectAll().mapNotNull { it[TokenTable.fcmToken] }
    }

    fun getByToken(token: String) = transaction {
        TokenTable.selectAll()
            .where { TokenTable.token eq token }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByRefreshToken(refreshToken: String) = transaction {
        TokenTable.selectAll()
            .where { TokenTable.refreshToken eq refreshToken }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: TokenCreateDto) = transaction {
        TokenTable.insert {
            it[userId] = dto.userId
            it[token] = dto.token
            it[refreshToken] = dto.refreshToken
            it[expiresAt] = dto.expiresAt
            it[fcmToken] = dto.fcmToken
            it[adminToken] = dto.adminToken
        }.let { getById(it[TokenTable.id]) }
    }

    override fun update(id: Int, dto: TokenUpdateDto) = transaction {
        TokenTable.update({ TokenTable.id eq id }) { updateStatement ->
            dto.userId?.let { updateStatement[TokenTable.userId] = it }
            dto.token?.let { updateStatement[TokenTable.token] = it }
            dto.refreshToken?.let { updateStatement[TokenTable.refreshToken] = it }
            dto.expiresAt?.let { updateStatement[TokenTable.expiresAt] = it }
            dto.fcmToken?.let { updateStatement[TokenTable.fcmToken] = it }
            dto.adminToken?.let { updateStatement[TokenTable.adminToken] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        TokenTable.deleteWhere { TokenTable.id eq id } > 0
    }
}