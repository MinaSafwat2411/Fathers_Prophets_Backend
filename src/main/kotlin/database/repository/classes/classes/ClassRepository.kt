package com.fathersprophets.backend.database.repository.classes.classes

import com.fathersprophets.backend.database.dao.classes.ClassDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.classes.ClassResponse
import com.fathersprophets.backend.models.classes.CreateClassRequest
import com.fathersprophets.backend.models.classes.UpdateClassRequest
import com.fathersprophets.backend.utils.Localization

class ClassRepository(
    private val classDao: ClassDao
) : IClassRepository {
    override suspend fun getAllClasses(lang: String): ApiResponse<List<ClassResponse>> {

        val classes = classDao.findAll()

        return ApiResponse(
            success = true,
            data = classes.map { it.toClassResponse() },
            message = Localization.get("classes_found", lang)
        )
    }

    override suspend fun getClassById(id: Int, lang: String): ApiResponse<ClassResponse> {
        val classData =
            classDao.findById(id) ?: throw IllegalArgumentException(Localization.get("class_not_found", lang))

        return ApiResponse(
            success = true, data = classData.toClassResponse(), message = Localization.get("class_found", lang)
        )
    }

    override suspend fun createClass(createClassRequest: CreateClassRequest, lang: String): ApiResponse<Int> {

        val id = classDao.createClass(createClassRequest.toClassDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("class_not_created", lang))


        return ApiResponse(success = true, data = id, message = Localization.get("class_created", lang))
    }

    override suspend fun updateClass(
        id: Int,
        updateClassRequest: UpdateClassRequest, lang: String
    ): ApiResponse<Nothing> {


        val updated = classDao.updateClass(updateClassRequest.toClassDto(id))

        if (!updated) throw IllegalArgumentException(Localization.get("class_not_updated", lang))


        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("class_updated", lang)
        )
    }

    override suspend fun deleteClass(id: Int, lang: String): ApiResponse<Nothing> {

        val  deleted = classDao.deleteClass(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("class_not_deleted", lang))

        return ApiResponse(success = true, data = null, message = Localization.get("class_deleted", lang))
    }
}
