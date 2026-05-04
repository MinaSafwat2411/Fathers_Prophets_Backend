package com.fathersprophets.backend.services.classmember

import com.fathersprophets.backend.database.repository.classmember.IClassMemberRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.dto.classmember.UpdateClassMemberRequest
import com.fathersprophets.backend.models.dto.classes.ClassMemberResponse
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class ClassMemberService(
    private val repository: IClassMemberRepository
): IClassMemberService {
    override suspend fun findMemberClass(
        classId: Int?,
        lang: String
    ): ApiResponse<List<ClassMemberResponse>> {
        validateRequired(classId to "class_id", lang = lang)
        return repository.findMemberClass(classId!!, lang)
    }

    override suspend fun addMember(
        addClassMemberRequest: AddClassMemberRequest,
        lang: String
    ): ApiResponse<Int> {
        validateRequired(
            addClassMemberRequest.userId to "user_id",
            addClassMemberRequest.classId to "class_id",
            addClassMemberRequest.isTeacher to "is_teacher",
            addClassMemberRequest.name to "name",
            lang = lang
        )
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
