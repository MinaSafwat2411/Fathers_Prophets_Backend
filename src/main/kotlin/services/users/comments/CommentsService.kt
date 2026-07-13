package com.fathersprophets.backend.services.users.comments

import com.fathersprophets.backend.database.repository.users.comments.ICommentsRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.comments.AddCommentRequest
import com.fathersprophets.backend.models.comments.CommentResponse
import com.fathersprophets.backend.models.comments.UpdateCommentRequest
import com.fathersprophets.backend.utils.CommentEventBroadcaster
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class CommentsService(
    private val commentsRepository: ICommentsRepository
) : ICommentsService {
    override fun addComment(
        comment: AddCommentRequest,
        lang: String
    ): ApiResponse<Int> {
        validateRequired(
            comment.userId to "user_id",
            comment.comment to "comment",
            lang = lang
        )
        val response = commentsRepository.addComment(comment, lang)
        
        // Broadcast to WebSocket clients
        if (response.success && response.data != null) {
            broadcastComment(response.data!!, response)
        }
        
        return response
    }

    override fun updateComment(
        commentId: Int?,
        comment: UpdateCommentRequest,
        lang: String
    ): ApiResponse<Nothing> {
        if (commentId == null) {
            throw IllegalArgumentException(Localization.get("comment_id_required", lang))
        }
        validateRequired(
            comment.comment to "comment",
            lang = lang
        )
        val response = commentsRepository.updateComment(commentId, comment, lang)
        
        // Broadcast to WebSocket clients
        if (response.success) {
            broadcastComment(commentId, response)
        }
        
        return response
    }

    override fun deleteComment(
        commentId: Int?,
        lang: String
    ): ApiResponse<Nothing> {
        if (commentId == null) {
            throw IllegalArgumentException(Localization.get("comment_id_required", lang))
        }
        return commentsRepository.deleteComment(commentId, lang)
    }

    override fun getCommentsByUserId(
        userId: Int,
        lang: String
    ): ApiResponse<List<CommentResponse>> {
        return commentsRepository.getCommentsByUserId(userId, lang)
    }

    override fun getAllComments(lang: String): ApiResponse<List<CommentResponse>> {
        return commentsRepository.getAllComments(lang)
    }

    private fun broadcastComment(id: Int, response: ApiResponse<*>) {
        thread(isDaemon = true) {
            runBlocking {
                CommentEventBroadcaster.broadcastComment(id, response)
            }
        }
    }
}
