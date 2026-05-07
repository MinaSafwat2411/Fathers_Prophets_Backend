package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.CommentsTable
import com.fathersprophets.backend.database.tables.CommentsTable.comment
import com.fathersprophets.backend.database.tables.CommentsTable.id
import com.fathersprophets.backend.database.tables.CommentsTable.userId
import com.fathersprophets.backend.models.dto.CommentDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class CommentDao {
    private fun rowToComment(row: ResultRow) = CommentDto(
        id = row[id],
        userId = row[userId],
        comment = row[comment],
    )
    
    fun addComment(comment: CommentDto) = transaction {
        CommentsTable.insert {
            it[userId] = comment.userId
            it[CommentsTable.comment] = comment.comment       
        } get CommentsTable.id
    }
    
    fun updateComment(comment: CommentDto) = transaction {
        CommentsTable.update({ CommentsTable.id eq comment.id }) {
            it[CommentsTable.comment] = comment.comment
        }

        return@transaction comment
    }
    
    fun deleteComment(comment: CommentDto) = transaction {
        CommentsTable.deleteWhere { CommentsTable.id eq comment.id }
    }

    fun getCommentById(commentId: Int) = transaction {
        CommentsTable.selectAll().where { CommentsTable.id eq commentId }
            .singleOrNull()?.let { rowToComment(it) }
    }

    fun getCommentsByUserId(userId: Int) = transaction {
        CommentsTable.selectAll().where { CommentsTable.userId eq userId }
            .map { rowToComment(it) }
    }

    fun getAllComments() = transaction {
        CommentsTable.selectAll().map { rowToComment(it) }
    }
}
