package com.fathersprophets.backend.database.repository.classmember

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.request.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.request.classmember.UpdateClassMemberRequest
import com.fathersprophets.backend.models.response.classes.ClassMemberResponse

interface IClassMemberRepository {
    suspend fun findMemberClass(classId: Int, lang: String): ApiResponse<List<ClassMemberResponse>>
    suspend fun addMember(addClassMemberRequest: AddClassMemberRequest, lang: String): ApiResponse<Int>
    suspend fun updateMember(id: Int, updateClassMemberRequest: UpdateClassMemberRequest, lang: String): ApiResponse<Nothing>
    suspend fun deleteMember(id: Int, lang: String): com.fathersprophets.backend.models.ApiResponse<Nothing>

}