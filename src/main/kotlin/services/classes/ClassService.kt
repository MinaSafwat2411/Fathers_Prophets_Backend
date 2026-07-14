package com.fathersprophets.backend.services.classes

import com.fathersprophets.backend.database.repository.classes.classes.IClassRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.classes.CreateClassRequest
import com.fathersprophets.backend.models.classes.UpdateClassRequest
import com.fathersprophets.backend.models.classes.ClassResponse
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class ClassService(
    private val classRepository: IClassRepository
) : IClassService {
    override suspend fun getAllClasses(lang: String): ApiResponse<List<ClassResponse>> {
        return classRepository.getAllClasses(lang)
    }

    override suspend fun getClassById(id: Int?, lang: String): ApiResponse<ClassResponse> {

        if (id == null) {
            throw IllegalArgumentException(Localization.get("class_id_required", lang))
        }

        return classRepository.getClassById(id, lang)
    }

    override suspend fun createClass(createClassRequest: CreateClassRequest, lang: String): ApiResponse<ClassResponse> {
        validateRequired(
            createClassRequest.name to "class_name",
            lang = lang
        )
        return classRepository.createClass(createClassRequest, lang)
    }

    override suspend fun updateClass(
        id: Int?,
        updateClassRequest: UpdateClassRequest,
        lang: String
    ): ApiResponse<ClassResponse> {

        if (id == null) {
            throw IllegalArgumentException(Localization.get("class_id_required", lang))
        }

        validateRequired(
            updateClassRequest.name to "class_name",
            lang = lang
        )

        return classRepository.updateClass(id, updateClassRequest, lang)
    }

    override suspend fun deleteClass(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) {
            throw IllegalArgumentException(Localization.get("class_id_required", lang))
        }
        return classRepository.deleteClass(id, lang)
    }
}
