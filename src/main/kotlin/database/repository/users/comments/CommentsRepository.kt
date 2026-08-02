package com.fathersprophets.backend.database.repository.users.comments

import com.fathersprophets.backend.database.dao.CommentDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.comments.AddCommentRequest
import com.fathersprophets.backend.models.comments.CommentResponse
import com.fathersprophets.backend.models.comments.UpdateCommentRequest
import com.fathersprophets.backend.utils.Localization

class CommentsRepository (
    private val commentDao: CommentDao
) : ICommentsRepository {
    override fun addComment(comment: AddCommentRequest,lang : String): ApiResponse<CommentResponse> {
        val create = commentDao.addComment(comment.toCommentDto())
            ?:throw IllegalArgumentException(Localization.get("comment_create_failed", lang))

        return ApiResponse(
            success = true,
            data = create.convertToCommentResponse(),
            message = Localization.get("comment_create_success", lang)
        )
    }

    override fun updateComment(commentId: Int,updateComment: UpdateCommentRequest,lang : String): ApiResponse<CommentResponse> {
        val updatedComment = commentDao.updateComment(updateComment.toCommentDto(commentId))
            ?:throw IllegalArgumentException(Localization.get("comment_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updatedComment.convertToCommentResponse(),
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
