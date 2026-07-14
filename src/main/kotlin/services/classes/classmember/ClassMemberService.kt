package com.fathersprophets.backend.services.classes.classmember

import com.fathersprophets.backend.database.repository.classes.classmember.IClassMemberRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.classmember.ClassMemberResponse
import com.fathersprophets.backend.models.classmember.UpdateClassMemberRequest
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class ClassMemberService(
    private val repository: IClassMemberRepository
) : IClassMemberService {
    override suspend fun findMemberClass(
        classId: Int?,
        lang: String
    ): ApiResponse<List<ClassMemberResponse>> {
        if (classId == null) throw IllegalArgumentException("class_id_required")
        return repository.findMemberClass(classId, lang)
    }

    override suspend fun addMember(
        addClassMemberRequest: AddClassMemberRequest,
        lang: String
    ): ApiResponse<ClassMemberResponse> {
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
        id: Int?,
        updateClassMemberRequest: UpdateClassMemberRequest,
        lang: String
    ): ApiResponse<ClassMemberResponse> {

        if (id == null) throw IllegalArgumentException("member_id_required")

        return repository.updateMember(id, updateClassMemberRequest, lang)
    }

    override suspend fun deleteMember(
        id: Int?,
        lang: String
    ): ApiResponse<Nothing> {

        if (id == null) throw IllegalArgumentException("member_id_required")

        return repository.deleteMember(id, lang)
    }
}
