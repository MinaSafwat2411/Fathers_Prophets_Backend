package com.fathersprophets.backend.modules.classes.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.base.BaseService
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.database.tables.classes.ClassCreateDto
import com.fathersprophets.backend.database.tables.classes.ClassDto
import com.fathersprophets.backend.database.tables.classes.ClassUpdateDto
import com.fathersprophets.backend.modules.classes.repository.ClassRepository
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class ClassService(
    classRepository: ClassRepository
) : BaseService<ClassDto, ClassCreateDto, ClassUpdateDto, ClassRepository>(classRepository), IClassService {

    override fun getAll(lang: String): ApiResponse<List<ClassDto>> {
        return ApiResponse(success = true, message = Localization.get("classes_found", lang), data = repository.getAll())
    }

    override fun getById(id: Int, lang: String): ApiResponse<ClassDto> {
        validateRequired(id to "class_id", lang = lang)
        val classDto = repository.getById(id) ?: throw NotFoundException(Localization.get("class_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("class_found", lang), data = classDto)
    }

    override fun getByFamilyId(familyId: Int, lang: String): ApiResponse<List<ClassDto>> {
        validateRequired(familyId to "family_id", lang = lang)
        return ApiResponse(
            success = true,
            message = Localization.get("classes_found", lang),
            data = repository.getByFamilyId(familyId)
        )
    }

    override fun create(dto: ClassCreateDto, lang: String): ApiResponse<ClassDto> {
        validateRequired(
            dto.name to "class_name",
            dto.familyId to "family_id",
            lang = lang
        )
        val created = repository.create(dto)
            ?: throw BadRequestException(Localization.get("could_not_create_class", lang))
        return ApiResponse(success = true, message = Localization.get("class_created", lang), data = created)
    }

    override fun update(id: Int, dto: ClassUpdateDto, lang: String): ApiResponse<ClassDto> {
        validateRequired(id to "class_id", lang = lang)
        val updated = repository.update(id, dto) ?: throw NotFoundException(Localization.get("class_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("class_updated", lang), data = updated)
    }

    override fun delete(id: Int, lang: String): ApiResponse<Nothing> {
        validateRequired(id to "class_id", lang = lang)
        if (!repository.delete(id)) throw NotFoundException(Localization.get("class_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("class_deleted", lang))
    }
}