package com.fathersprophets.backend.modules.classes.repository

import com.fathersprophets.backend.modules.classes.ClassDto

interface IClassRepository {
    fun getByFamilyId(familyId: Int): List<ClassDto>
}