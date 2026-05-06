package com.fathersprophets.backend.database.repository.classmember

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.classmember.ClassMemberResponse
import com.fathersprophets.backend.models.classmember.UpdateClassMemberRequest
import com.fathersprophets.backend.models.dto.ClassMemberDto

interface IClassMemberRepository {
    suspend fun findMemberClass(classId: Int, lang: String): ApiResponse<List<ClassMemberResponse>>
    suspend fun addMember(addClassMemberRequest: AddClassMemberRequest, lang: String): ApiResponse<ClassMemberResponse>
    suspend fun updateMember(id: Int, updateClassMemberRequest: UpdateClassMemberRequest, lang: String): ApiResponse<ClassMemberResponse>
    suspend fun deleteMember(id: Int, lang: String): ApiResponse<Nothing>

}