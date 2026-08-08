package com.fathersprophets.backend.modules.classes.repository

import com.fathersprophets.backend.database.tables.classes.ClassDto

interface IClassRepository {
    fun getByFamilyId(familyId: Int): List<ClassDto>
}