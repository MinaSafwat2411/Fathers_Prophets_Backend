package com.fathersprophets.backend.database.repository.classes

import com.fathersprophets.backend.database.dao.ClassDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.classes.CreateClassRequest
import com.fathersprophets.backend.models.dto.classes.UpdateClassRequest
import com.fathersprophets.backend.models.dto.classes.ClassResponse
import com.fathersprophets.backend.utils.Localization

class ClassRepository(
    private val classDao: ClassDao
) : IClassRepository {
    override suspend fun getAllClasses(lang: String): ApiResponse<List<ClassResponse>> {
        val classes = classDao.findAll().map {
            ClassResponse(it.id, it.name, it.image)
        }
        return ApiResponse(success = true, data = classes, message = Localization.get("classes_found",lang))
    }

    override suspend fun getClassById(id: Int, lang: String): ApiResponse<ClassResponse?> {
        val classData = classDao.findById(id)?.let {
            ClassResponse(it.id, it.name, it.image)
        }
        return ApiResponse(success = true, data = classData, message = Localization.get(if (classData != null) "class_found" else "class_not_found", lang))
    }

    override suspend fun createClass(createClassRequest: CreateClassRequest, lang: String): ApiResponse<Int> {
        val data = mapOf(
            "name" to createClassRequest.name,
            "image" to createClassRequest.image
        )
        val id = classDao.createClass(data)
        return ApiResponse(success = true, data = id, message = Localization.get("class_created", lang))
    }

    override suspend fun updateClass(
        id: Int,
        updateClassRequest: UpdateClassRequest, 
        lang: String
    ): ApiResponse<Nothing> {
        classDao.updateClass(id, updateClassRequest)
        return ApiResponse(success = true, data = null, message = Localization.get("class_updated", lang))
    }

    override suspend fun deleteClass(id: Int, lang: String): ApiResponse<Nothing> {
        classDao.deleteClass(id)
        return ApiResponse(success = true, data = null, message = Localization.get("class_deleted", lang))
    }
}
