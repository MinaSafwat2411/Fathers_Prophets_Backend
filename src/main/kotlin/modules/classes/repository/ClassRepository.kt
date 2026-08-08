package com.fathersprophets.backend.modules.classes.repository

import com.fathersprophets.backend.base.BaseRepository
import com.fathersprophets.backend.database.tables.classes.ClassCreateDto
import com.fathersprophets.backend.database.tables.classes.ClassDao
import com.fathersprophets.backend.database.tables.classes.ClassDto
import com.fathersprophets.backend.database.tables.classes.ClassUpdateDto

class ClassRepository(
    classDao: ClassDao
) : BaseRepository<ClassDto, ClassCreateDto, ClassUpdateDto, ClassDao>(classDao), IClassRepository {

    override fun getByFamilyId(familyId: Int): List<ClassDto> = dao.getByFamilyId(familyId)
}