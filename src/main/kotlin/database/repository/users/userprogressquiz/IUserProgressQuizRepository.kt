package com.fathersprophets.backend.database.repository.userprogressquiz

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.userprogressquiz.CreateUserProgressQuizRequest
import com.fathersprophets.backend.models.userprogressquiz.UpdateUserProgressQuizRequest
import com.fathersprophets.backend.models.userprogressquiz.UserProgressQuizResponse

interface IUserProgressQuizRepository {
    fun getAllUserProgress(lang: String): ApiResponse<List<UserProgressQuizResponse>>
    fun getUserProgressById(id: Int, lang: String): ApiResponse<UserProgressQuizResponse>
    fun getUserProgressByUserId(userId: Int, lang: String): ApiResponse<List<UserProgressQuizResponse>>
    fun getUserProgressByQuizId(quizId: Int, lang: String): ApiResponse<List<UserProgressQuizResponse>>
    fun createUserProgress(request: CreateUserProgressQuizRequest, lang: String): ApiResponse<Int>
    fun updateUserProgress(id: Int, request: UpdateUserProgressQuizRequest, lang: String): ApiResponse<Nothing>
    fun deleteUserProgress(id: Int, lang: String): ApiResponse<Nothing>
}