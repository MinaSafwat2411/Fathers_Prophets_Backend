package com.fathersprophets.backend.services.classmember

import com.fathersprophets.backend.models.dto.classes.ClassMemberResponse

interface IClassMemberService {
    suspend fun findMemberClass(classId: Int, lang: String): com.fathersprophets.backend.models.ApiResponse<List<ClassMemberResponse>>
    suspend fun addMember(addClassMemberRequest: com.fathersprophets.backend.models.request.classmember.AddClassMemberRequest, lang: String): com.fathersprophets.backend.models.ApiResponse<Int>
    suspend fun updateMember(id: Int, updateClassMemberRequest: com.fathersprophets.backend.models.request.classmember.UpdateClassMemberRequest, lang: String): com.fathersprophets.backend.models.ApiResponse<Nothing>
    suspend fun deleteMember(id: Int, lang: String): com.fathersprophets.backend.models.ApiResponse<Nothing>
}