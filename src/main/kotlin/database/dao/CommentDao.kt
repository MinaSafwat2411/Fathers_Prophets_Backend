package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.CommentsTable
import com.fathersprophets.backend.database.dto.CommentDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class CommentDao {
    private fun rowToComment(row: ResultRow) = CommentDto(
        id = row[CommentsTable.id],
        userId = row[CommentsTable.userId],
        comment = row[CommentsTable.comment],
    )

    fun addComment(comment: CommentDto) = transaction {
        CommentsTable.insert {
            it[CommentsTable.comment] = comment.comment
            it[CommentsTable.userId] = comment.userId
        }.resultedValues?.singleOrNull()?.let { rowToComment(it) }
    }

    fun updateComment(comment: CommentDto) = transaction {
        CommentsTable.update({ CommentsTable.id eq comment.id }) {
            it[CommentsTable.comment] = comment.comment
        }.let { getCommentById(comment.id) }
    }

    fun deleteComment(commentId: Int) = transaction {
        CommentsTable.deleteWhere { CommentsTable.id eq commentId } > 0
    }

    fun getCommentById(commentId: Int) = transaction {
        CommentsTable.selectAll().where { CommentsTable.id eq commentId }
            .singleOrNull()?.let { rowToComment(it) }
    }

    fun getCommentsByUserId(userId: Int) = transaction {
        CommentsTable.selectAll().where { CommentsTable.userId eq userId }
            .map { rowToComment(it) }
    }
}