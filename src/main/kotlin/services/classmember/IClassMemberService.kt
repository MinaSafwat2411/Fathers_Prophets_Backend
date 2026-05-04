package com.fathersprophets.backend.services.classmember

import com.fathersprophets.backend.models.dto.classes.ClassMemberResponse
import com.fathersprophets.backend.models.dto.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.dto.classmember.UpdateClassMemberRequest

interface IClassMemberService {
    suspend fun findMemberClass(classId: Int?, lang: String): com.fathersprophets.backend.models.ApiResponse<List<ClassMemberResponse>>
    suspend fun addMember(addClassMemberRequest: AddClassMemberRequest, lang: String): com.fathersprophets.backend.models.ApiResponse<Int>
    suspend fun updateMember(id: Int, updateClassMemberRequest: UpdateClassMemberRequest, lang: String): com.fathersprophets.backend.models.ApiResponse<Nothing>
    suspend fun deleteMember(id: Int, lang: String): com.fathersprophets.backend.models.ApiResponse<Nothing>
}