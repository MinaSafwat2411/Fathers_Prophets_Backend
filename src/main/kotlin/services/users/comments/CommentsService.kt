package com.fathersprophets.backend.services.comments

import com.fathersprophets.backend.database.repository.users.comments.ICommentsRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.comments.AddCommentRequest
import com.fathersprophets.backend.models.comments.CommentResponse
import com.fathersprophets.backend.models.comments.UpdateCommentRequest
import com.fathersprophets.backend.utils.CommentEventBroadcaster
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class CommentsService(
    private val commentsRepository: ICommentsRepository
) : ICommentsService {
    override fun addComment(
        comment: AddCommentRequest,
        lang: String
    ): ApiResponse<CommentResponse> {
        validateRequired(
            comment.userId to "user_id",
            comment.comment to "comment",
            lang = lang
        )
        val response = commentsRepository.addComment(comment, lang)
        
        // Broadcast to WebSocket clients
        if (response.success && response.data != null) {
            broadcastComment(response.data.userId, response)
        }
        
        return response
    }

    override fun updateComment(
        commentId: Int?,
        comment: UpdateCommentRequest,
        lang: String
    ): ApiResponse<CommentResponse> {
        if (commentId==null){
            throw IllegalArgumentException("comment_id_is_null")
        }
        validateRequired(
            comment.comment to "comment",
            lang = lang
        )
        val response = commentsRepository.updateComment(commentId,comment, lang)
        
        // Broadcast to WebSocket clients
        if (response.success && response.data != null) {
            broadcastComment(response.data.userId, response)
        }
        
        return response
    }

    override fun deleteComment(
        commentId: Int?,
        lang: String
    ): ApiResponse<Nothing> {
        if(commentId==null){
            throw IllegalArgumentException("comment_id_is_null")
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

    private fun broadcastComment(userId: Int, response: ApiResponse<CommentResponse>) {
        thread(isDaemon = true) {
            runBlocking {
                CommentEventBroadcaster.broadcastComment(userId, response)
            }
        }
    }
}
