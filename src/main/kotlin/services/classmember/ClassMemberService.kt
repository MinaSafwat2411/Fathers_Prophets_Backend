package com.fathersprophets.backend.services.classmember

import com.fathersprophets.backend.database.repository.classmember.IClassMemberRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.request.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.request.classmember.UpdateClassMemberRequest
import com.fathersprophets.backend.models.response.classes.ClassMemberResponse

class ClassMemberService(
    private val repository: IClassMemberRepository
): IClassMemberService {
    override suspend fun findMemberClass(
        classId: Int,
        lang: String
    ): ApiResponse<List<ClassMemberResponse>> {
        return repository.findMemberClass(classId, lang)
    }

    override suspend fun addMember(
        addClassMemberRequest: AddClassMemberRequest,
        lang: String
    ): ApiResponse<Int> {
        return repository.addMember(addClassMemberRequest, lang)
    }

    override suspend fun updateMember(
        id: Int,
        updateClassMemberRequest: UpdateClassMemberRequest,
        lang: String
    ): ApiResponse<Nothing> {
        return repository.updateMember(id, updateClassMemberRequest, lang)
    }

    override suspend fun deleteMember(
        id: Int,
        lang: String
    ): ApiResponse<Nothing> {
        return repository.deleteMember(id, lang)
    }
}
