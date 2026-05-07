package com.fathersprophets.backend.services.comments

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.comments.AddCommentRequest
import com.fathersprophets.backend.models.comments.CommentResponse
import com.fathersprophets.backend.models.comments.UpdateCommentRequest

interface ICommentsService {
    fun addComment(comment: AddCommentRequest, lang : String): ApiResponse<CommentResponse>
    fun updateComment(comment: UpdateCommentRequest, lang : String): ApiResponse<CommentResponse>
    fun deleteComment(commentId : Int,lang : String): ApiResponse<Nothing>
    fun getCommentsByUserId(userId: Int,lang : String): ApiResponse<List<CommentResponse>>
    fun getAllComments(lang : String): ApiResponse<List<CommentResponse>>
}