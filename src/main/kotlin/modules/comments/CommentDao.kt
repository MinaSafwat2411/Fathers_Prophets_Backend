package com.fathersprophets.backend.modules.comments

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class CommentDao : CrudDao<CommentDto, CommentCreateDto, CommentUpdateDto> {

    private fun ResultRow.toDto() = CommentDto(
        id = this[CommentsTable.id],
        userId = this[CommentsTable.userId],
        comment = this[CommentsTable.comment],
        teacherId = this[CommentsTable.teacherId]
    )

    override fun getAll() = transaction {
        CommentsTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        CommentsTable.selectAll()
            .where { CommentsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByUserId(userId: Int) = transaction {
        CommentsTable.selectAll()
            .where { CommentsTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByTeacherId(teacherId: Int) = transaction {
        CommentsTable.selectAll()
            .where { CommentsTable.teacherId eq teacherId }
            .map { it.toDto() }
    }

    override fun create(dto: CommentCreateDto) = transaction {
        CommentsTable.insert {
            it[userId] = dto.userId
            it[comment] = dto.comment
            it[teacherId] = dto.teacherId
        }.let { getById(it[CommentsTable.id]) }
    }

    override fun update(id: Int, dto: CommentUpdateDto) = transaction {
        CommentsTable.update({ CommentsTable.id eq id }) { updateStatement ->
            dto.comment?.let { updateStatement[CommentsTable.comment] = it }
            dto.teacherId?.let { updateStatement[CommentsTable.teacherId] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        CommentsTable.deleteWhere { CommentsTable.id eq id } > 0
    }
}