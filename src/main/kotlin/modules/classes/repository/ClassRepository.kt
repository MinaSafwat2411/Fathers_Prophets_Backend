package com.fathersprophets.backend.modules.classes.repository

import com.fathersprophets.backend.base.BaseRepository
import com.fathersprophets.backend.modules.classes.ClassCreateDto
import com.fathersprophets.backend.modules.classes.ClassDao
import com.fathersprophets.backend.modules.classes.ClassDto
import com.fathersprophets.backend.modules.classes.ClassUpdateDto

class ClassRepository(
    classDao: ClassDao
) : BaseRepository<ClassDto, ClassCreateDto, ClassUpdateDto, ClassDao>(classDao), IClassRepository {

    override fun getByFamilyId(familyId: Int): List<ClassDto> = dao.getByFamilyId(familyId)
}