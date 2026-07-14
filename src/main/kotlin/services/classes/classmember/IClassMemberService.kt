package com.fathersprophets.backend.services.classes.classmember

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.classmember.ClassMemberResponse
import com.fathersprophets.backend.models.classmember.UpdateClassMemberRequest

interface IClassMemberService {
    suspend fun findMemberClass(classId: Int?, lang: String): ApiResponse<List<ClassMemberResponse>>
    suspend fun addMember(addClassMemberRequest: AddClassMemberRequest, lang: String): ApiResponse<ClassMemberResponse>
    suspend fun updateMember(id: Int?, updateClassMemberRequest: UpdateClassMemberRequest, lang: String): ApiResponse<ClassMemberResponse>
    suspend fun deleteMember(id: Int?, lang: String): ApiResponse<Nothing>
}