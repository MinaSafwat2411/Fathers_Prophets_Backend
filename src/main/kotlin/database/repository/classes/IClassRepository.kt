package com.fathersprophets.backend.database.repository.classes

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.request.classes.CreateClassRequest
import com.fathersprophets.backend.models.request.classes.UpdateClassRequest
import com.fathersprophets.backend.models.response.classes.ClassResponse

interface IClassRepository {
    suspend fun getAllClasses(lang: String): ApiResponse<List<ClassResponse>>
    suspend fun getClassById(id: Int, lang: String): ApiResponse<ClassResponse?>
    suspend fun createClass(createClassRequest: CreateClassRequest, lang: String): ApiResponse<Int>
    suspend fun updateClass(id: Int, updateClassRequest: UpdateClassRequest, lang: String): ApiResponse<Nothing>
    suspend fun deleteClass(id: Int, lang: String): ApiResponse<Nothing>

}