package com.fathersprophets.backend.modules.family.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.database.tables.family.FamilyCreateDto
import com.fathersprophets.backend.database.tables.family.FamilyDto
import com.fathersprophets.backend.database.tables.family.FamilyUpdateDto

interface IFamilyService {
    fun getAll(lang: String): ApiResponse<List<FamilyDto>>
    fun getById(id: Int, lang: String): ApiResponse<FamilyDto>
    fun create(dto: FamilyCreateDto, lang: String): ApiResponse<FamilyDto>
    fun update(id: Int, dto: FamilyUpdateDto, lang: String): ApiResponse<FamilyDto>
    fun delete(id: Int, lang: String): ApiResponse<Nothing>
}