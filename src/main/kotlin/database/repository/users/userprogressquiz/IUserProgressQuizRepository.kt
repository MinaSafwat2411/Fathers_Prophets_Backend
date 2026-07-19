package com.fathersprophets.backend.database.repository.users.userprogressquiz

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.userprogressquiz.CreateUserProgressQuizRequest
import com.fathersprophets.backend.models.userprogressquiz.UpdateUserProgressQuizRequest
import com.fathersprophets.backend.models.userprogressquiz.UserProgressQuizResponse

interface IUserProgressQuizRepository {
    fun getAllUserProgress(lang: String): ApiResponse<List<UserProgressQuizResponse>>
    fun getUserProgressByUserId(userId: Int, lang: String): ApiResponse<List<UserProgressQuizResponse>>
    fun createUserProgress(request: CreateUserProgressQuizRequest, lang: String): ApiResponse<UserProgressQuizResponse>
    fun updateUserProgress(id: Int, request: UpdateUserProgressQuizRequest, lang: String): ApiResponse<UserProgressQuizResponse>
    fun deleteUserProgress(id: Int, lang: String): ApiResponse<Nothing>
}