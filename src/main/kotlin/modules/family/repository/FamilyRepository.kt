package com.fathersprophets.backend.modules.family.repository

import com.fathersprophets.backend.base.BaseRepository
import com.fathersprophets.backend.database.tables.family.FamilyCreateDto
import com.fathersprophets.backend.database.tables.family.FamilyDao
import com.fathersprophets.backend.database.tables.family.FamilyDto
import com.fathersprophets.backend.database.tables.family.FamilyUpdateDto

class FamilyRepository(
    familyDao: FamilyDao
) : BaseRepository<FamilyDto, FamilyCreateDto, FamilyUpdateDto, FamilyDao>(familyDao)