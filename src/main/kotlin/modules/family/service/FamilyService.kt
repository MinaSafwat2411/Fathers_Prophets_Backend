package com.fathersprophets.backend.modules.family.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.base.BaseService
import com.fathersprophets.backend.database.tables.family.FamilyCreateDto
import com.fathersprophets.backend.database.tables.family.FamilyDto
import com.fathersprophets.backend.database.tables.family.FamilyUpdateDto
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.modules.family.repository.FamilyRepository
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class FamilyService(
    familyRepository: FamilyRepository
) : BaseService<FamilyDto, FamilyCreateDto, FamilyUpdateDto, FamilyRepository>(familyRepository), IFamilyService {

    override fun getAll(lang: String): ApiResponse<List<FamilyDto>> {
        return ApiResponse(
            success = true,
            message = Localization.get("families_found", lang),
            data = repository.getAll()
        )
    }

    override fun getById(id: Int, lang: String): ApiResponse<FamilyDto> {
        validateRequired(id to "family_id", lang = lang)
        val family = repository.getById(id) ?: throw NotFoundException(Localization.get("family_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("family_found", lang), data = family)
    }

    override fun create(dto: FamilyCreateDto, lang: String): ApiResponse<FamilyDto> {
        validateRequired(
            dto.familyName to "family_name",
            dto.leaderId to "leader_id",
            dto.subLeaderId to "sub_leader_id",
            lang = lang
        )
        val created = repository.create(dto)
            ?: throw BadRequestException(Localization.get("could_not_create_family", lang))
        return ApiResponse(success = true, message = Localization.get("family_created", lang), data = created)
    }

    override fun update(id: Int, dto: FamilyUpdateDto, lang: String): ApiResponse<FamilyDto> {
        validateRequired(id to "family_id", lang = lang)
        val updated = repository.update(id, dto) ?: throw NotFoundException(Localization.get("family_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("family_updated", lang), data = updated)
    }

    override fun delete(id: Int, lang: String): ApiResponse<Nothing> {
        validateRequired(id to "family_id", lang = lang)
        if (!repository.delete(id)) throw NotFoundException(Localization.get("family_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("family_deleted", lang))
    }
}