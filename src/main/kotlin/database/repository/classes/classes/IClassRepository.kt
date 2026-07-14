package com.fathersprophets.backend.database.repository.classes.classes

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.classes.CreateClassRequest
import com.fathersprophets.backend.models.classes.UpdateClassRequest
import com.fathersprophets.backend.models.classes.ClassResponse

interface IClassRepository {
    suspend fun getAllClasses(lang: String): ApiResponse<List<ClassResponse>>
    suspend fun getClassById(id: Int, lang: String): ApiResponse<ClassResponse>
    suspend fun createClass(createClassRequest: CreateClassRequest, lang: String): ApiResponse<ClassResponse>
    suspend fun updateClass(id: Int, updateClassRequest: UpdateClassRequest, lang: String): ApiResponse<ClassResponse>
    suspend fun deleteClass(id: Int, lang: String): ApiResponse<Nothing>

}