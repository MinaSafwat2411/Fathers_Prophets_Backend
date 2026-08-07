package com.fathersprophets.backend.modules.classes.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.modules.classes.ClassCreateDto
import com.fathersprophets.backend.modules.classes.ClassDto
import com.fathersprophets.backend.modules.classes.ClassUpdateDto

interface IClassService {
    fun getAll(lang: String): ApiResponse<List<ClassDto>>
    fun getById(id: Int, lang: String): ApiResponse<ClassDto>
    fun getByFamilyId(familyId: Int, lang: String): ApiResponse<List<ClassDto>>
    fun create(dto: ClassCreateDto, lang: String): ApiResponse<ClassDto>
    fun update(id: Int, dto: ClassUpdateDto, lang: String): ApiResponse<ClassDto>
    fun delete(id: Int, lang: String): ApiResponse<Nothing>
}