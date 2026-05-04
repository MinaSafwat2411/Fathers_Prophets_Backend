package com.fathersprophets.backend.services.classes

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.classes.CreateClassRequest
import com.fathersprophets.backend.models.dto.classes.UpdateClassRequest
import com.fathersprophets.backend.models.dto.classes.ClassResponse

interface IClassService {
    suspend fun getAllClasses(lang: String): ApiResponse<List<ClassResponse>>
    suspend fun getClassById(id: Int?, lang: String): ApiResponse<ClassResponse?>
    suspend fun createClass(createClassRequest: CreateClassRequest, lang: String): ApiResponse<Int>
    suspend fun updateClass(updateClassRequest: UpdateClassRequest, lang: String): ApiResponse<Nothing>
    suspend fun deleteClass(id: Int, lang: String): ApiResponse<Nothing>

}