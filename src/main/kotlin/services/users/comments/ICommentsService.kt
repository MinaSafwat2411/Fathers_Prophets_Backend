package com.fathersprophets.backend.services.users.comments

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.comments.AddCommentRequest
import com.fathersprophets.backend.models.comments.CommentResponse
import com.fathersprophets.backend.models.comments.UpdateCommentRequest

interface ICommentsService {
    fun addComment(comment: AddCommentRequest, lang : String): ApiResponse<Int>
    fun updateComment(commentId : Int?,comment: UpdateCommentRequest, lang : String): ApiResponse<Nothing>
    fun deleteComment(commentId : Int?,lang : String): ApiResponse<Nothing>
    fun getCommentsByUserId(userId: Int,lang : String): ApiResponse<List<CommentResponse>>
    fun getAllComments(lang : String): ApiResponse<List<CommentResponse>>
}