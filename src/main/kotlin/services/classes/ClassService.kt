package com.fathersprophets.backend.services.classes

import com.fathersprophets.backend.database.repository.classes.IClassRepository
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.request.classes.CreateClassRequest
import com.fathersprophets.backend.models.request.classes.UpdateClassRequest
import com.fathersprophets.backend.models.response.classes.ClassResponse
import com.fathersprophets.backend.utils.Localization

class ClassService(
    private val classRepository: IClassRepository
) : IClassService {
    override suspend fun getAllClasses(lang: String): ApiResponse<List<ClassResponse>> {
        return classRepository.getAllClasses(lang)
    }

    override suspend fun getClassById(id: Int, lang: String): ApiResponse<ClassResponse?> {
        val classResponse = classRepository.getClassById(id, lang)
        if (classResponse.data == null) {
            throw NotFoundException(Localization.get("class_not_found", lang))
        }
        return classResponse
    }

    override suspend fun createClass(createClassRequest: CreateClassRequest, lang: String): ApiResponse<Int> {
        if (createClassRequest.name.isBlank()) {
            throw BadRequestException(Localization.get("class_name_empty", lang))
        }
        return classRepository.createClass(createClassRequest, lang)
    }

    override suspend fun updateClass(updateClassRequest: UpdateClassRequest, lang: String): ApiResponse<Nothing> {
        val classExists = classRepository.getClassById(updateClassRequest.id, lang).data != null
        if (!classExists) {
            throw NotFoundException(Localization.get("class_not_found", lang))
        }
        if (updateClassRequest.name.isBlank()) {
            throw BadRequestException(Localization.get("class_name_empty", lang))
        }
        return classRepository.updateClass(updateClassRequest.id, updateClassRequest, lang)
    }

    override suspend fun deleteClass(id: Int, lang: String): ApiResponse<Nothing> {
        val classExists = classRepository.getClassById(id, lang).data != null
        if (!classExists) {
            throw NotFoundException(Localization.get("class_not_found", lang))
        }
        return classRepository.deleteClass(id, lang)
    }
}
