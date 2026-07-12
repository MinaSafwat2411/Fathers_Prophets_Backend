package com.fathersprophets.backend.database.repository.users.comments

import com.fathersprophets.backend.database.dao.users.CommentDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.comments.AddCommentRequest
import com.fathersprophets.backend.models.comments.CommentResponse
import com.fathersprophets.backend.models.comments.UpdateCommentRequest
import com.fathersprophets.backend.utils.Localization

class CommentsRepository (
    private val commentDao: CommentDao
) : ICommentsRepository {
    override fun addComment(comment: AddCommentRequest,lang : String): ApiResponse<Int> {
        val id = commentDao.addComment(comment.toCommentDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("comment_create_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("comment_create_success", lang)
        )
    }

    override fun updateComment(commentId: Int,updateComment: UpdateCommentRequest,lang : String): ApiResponse<Nothing> {
        val updatedComment = commentDao.updateComment(updateComment.toCommentDto(commentId))

        if (!updatedComment) throw IllegalArgumentException(Localization.get("comment_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("comment_update_success", lang)
        )
    }

    override fun deleteComment(commentId : Int,lang : String): ApiResponse<Nothing> {

        val deleted = commentDao.deleteComment(commentId)

        if (!deleted) throw IllegalArgumentException(Localization.get("comment_delete_failed", lang))
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
}
