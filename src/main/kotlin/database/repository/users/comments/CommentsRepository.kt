package com.fathersprophets.backend.database.repository.users.comments

import com.fathersprophets.backend.database.dao.users.CommentDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.comments.AddCommentRequest
import com.fathersprophets.backend.models.comments.CommentResponse
import com.fathersprophets.backend.models.comments.UpdateCommentRequest
import com.fathersprophets.backend.models.dto.CommentDto
import com.fathersprophets.backend.utils.Localization

class CommentsRepository (
    private val commentDao: CommentDao
) : ICommentsRepository {
    override fun addComment(comment: AddCommentRequest,lang : String): ApiResponse<CommentResponse> {
        val id = commentDao.addComment(comment.toCommentDto())
        val createdComment = commentDao.getCommentById(id)?:
            throw IllegalStateException("comment_create_failed")

        return ApiResponse(
            success = true,
            data = createdComment.convertToCommentResponse(),
            message = Localization.get("comment_create_success", lang)
        )
    }

    override fun updateComment(commentId: Int,updateComment: UpdateCommentRequest,lang : String): ApiResponse<CommentResponse> {
        val updatedComment = commentDao.updateComment(updateComment.toCommentDto(commentId))
        return ApiResponse(
            success = true,
            data = updatedComment.convertToCommentResponse(),
            message = Localization.get("comment_update_success", lang)
        )
    }

    override fun deleteComment(commentId : Int,lang : String): ApiResponse<Nothing> {
        commentDao.deleteComment(idToComment(commentId))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("comment_delete_success", lang)
        )
    }

    override fun getCommentsByUserId(userId: Int,lang : String): ApiResponse<List<CommentResponse>> {
        val comments = commentDao.getCommentsByUserId(userId)

        return ApiResponse(
            success = true,
            data = comments.map { it.convertToCommentResponse() },
            message = Localization.get("comments_retrieved_success", lang)
        )
    }

    override fun getAllComments(lang : String): ApiResponse<List<CommentResponse>> {
        val  comments = commentDao.getAllComments()
        return ApiResponse(
            success = true,
            data = comments.map { it.convertToCommentResponse() },
            message = Localization.get("comments_retrieved_success", lang)
        )
    }

    fun idToComment(commentId : Int)=CommentDto(
        id = commentId,
        userId = 0,
        comment = ""
    )
}
