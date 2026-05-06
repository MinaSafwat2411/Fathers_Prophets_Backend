package com.fathersprophets.backend.database.repository.classes

import com.fathersprophets.backend.database.dao.ClassDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.classes.ClassResponse
import com.fathersprophets.backend.models.classes.CreateClassRequest
import com.fathersprophets.backend.models.classes.UpdateClassRequest
import com.fathersprophets.backend.models.dto.ClassDto
import com.fathersprophets.backend.utils.Localization

class ClassRepository(
    private val classDao: ClassDao
) : IClassRepository {
    override suspend fun getAllClasses(lang: String): ApiResponse<List<ClassResponse>> {
        val classes = classDao.findAll().map {
            it.toClassResponse()
        }
        return ApiResponse(success = true, data = classes, message = Localization.get("classes_found", lang))
    }

    override suspend fun getClassById(id: Int, lang: String): ApiResponse<ClassResponse> {
        val classDto = ClassDto(
            id = id, name = "", image = ""
        )
        val classData =
            classDao.findById(classDto) ?: throw IllegalArgumentException(Localization.get("class_not_found", lang))

        return ApiResponse(
            success = true, data = classData.toClassResponse(), message = Localization.get("class_found", lang)
        )
    }

    override suspend fun createClass(createClassRequest: CreateClassRequest, lang: String): ApiResponse<ClassResponse> {
        val classDto = createClassRequest.toClassDto()

        val id = classDao.createClass(classDto)

        val classData = classDao.findById(classDto.copy(id = id))
            ?: throw IllegalArgumentException(Localization.get("could_not_create_class", lang))

        return ApiResponse(
            success = true, data = classData.toClassResponse(), message = Localization.get("class_created", lang)
        )
    }

    override suspend fun updateClass(
        id: Int,
        updateClassRequest: UpdateClassRequest, lang: String
    ): ApiResponse<ClassResponse> {

        val classDto = updateClassRequest.toClassDto(id)

        classDao.updateClass(classDto)

        return ApiResponse(
            success = true,
            data = updateClassRequest.toClassDto(id).toClassResponse(),
            message = Localization.get("class_updated", lang)
        )
    }

    override suspend fun deleteClass(id: Int, lang: String): ApiResponse<Nothing> {
        val classDto = ClassDto(
            id = id, name = "", image = ""
        )
        classDao.deleteClass(classDto)
        return ApiResponse(success = true, data = null, message = Localization.get("class_deleted", lang))
    }
}
