package com.fathersprophets.backend.database.repository.classes.classmember

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.classmember.ClassMemberResponse
import com.fathersprophets.backend.models.classmember.UpdateClassMemberRequest

interface IClassMemberRepository {
    suspend fun findMemberClass(classId: Int, lang: String): ApiResponse<List<ClassMemberResponse>>
    suspend fun addMember(addClassMemberRequest: AddClassMemberRequest, lang: String): ApiResponse<Int>
    suspend fun updateMember(id: Int, updateClassMemberRequest: UpdateClassMemberRequest, lang: String): ApiResponse<Nothing>
    suspend fun deleteMember(id: Int, lang: String): ApiResponse<Nothing>

}