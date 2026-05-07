package com.fathersprophets.backend.database.repository.comments

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.comments.AddCommentRequest
import com.fathersprophets.backend.models.comments.CommentResponse
import com.fathersprophets.backend.models.comments.UpdateCommentRequest
import com.fathersprophets.backend.models.dto.CommentDto

interface ICommentsRepository {
    fun addComment(comment: AddCommentRequest,lang : String): ApiResponse<CommentResponse>
    fun updateComment(updateComment: UpdateCommentRequest,lang : String): ApiResponse<CommentResponse>
    fun deleteComment(commentId : Int,lang : String): ApiResponse<Nothing>
    fun getCommentsByUserId(userId: Int,lang : String): ApiResponse<List<CommentResponse>>
    fun getAllComments(lang : String): ApiResponse<List<CommentResponse>>
}
