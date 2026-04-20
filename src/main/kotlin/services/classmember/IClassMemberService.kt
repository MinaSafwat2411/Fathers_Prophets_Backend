package com.fathersprophets.backend.services.classmember

interface IClassMemberService {
    suspend fun findMemberClass(classId: Int, lang: String): com.fathersprophets.backend.models.ApiResponse<List<com.fathersprophets.backend.models.response.classes.ClassMemberResponse>>
    suspend fun addMember(addClassMemberRequest: com.fathersprophets.backend.models.request.classmember.AddClassMemberRequest, lang: String): com.fathersprophets.backend.models.ApiResponse<Int>
    suspend fun updateMember(id: Int, updateClassMemberRequest: com.fathersprophets.backend.models.request.classmember.UpdateClassMemberRequest, lang: String): com.fathersprophets.backend.models.ApiResponse<Nothing>
    suspend fun deleteMember(id: Int, lang: String): com.fathersprophets.backend.models.ApiResponse<Nothing>
}