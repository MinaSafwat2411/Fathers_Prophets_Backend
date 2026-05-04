package com.fathersprophets.backend.database.repository.classmember

import com.fathersprophets.backend.database.dao.ClassMemberDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.request.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.request.classmember.UpdateClassMemberRequest
import com.fathersprophets.backend.models.dto.classes.ClassMemberResponse
import com.fathersprophets.backend.utils.Localization

class ClassMemberRepository(
    private val classMemberDao: ClassMemberDao
): IClassMemberRepository  {
    override suspend fun findMemberClass(
        classId: Int,
        lang: String
    ): ApiResponse<List<ClassMemberResponse>> {
        val members = classMemberDao.findMemberClass(classId)
        return ApiResponse(success = true, data = members, message = Localization.get("class_members_found", lang))
    }

    override suspend fun addMember(
        addClassMemberRequest: AddClassMemberRequest,
        lang: String
    ): ApiResponse<Int> {
        val id = classMemberDao.addMember(addClassMemberRequest)
        return ApiResponse(success = true, data = id, message = Localization.get("class_member_added", lang))
    }

    override suspend fun updateMember(
        id: Int,
        updateClassMemberRequest: UpdateClassMemberRequest,
        lang: String
    ): ApiResponse<Nothing> {
        classMemberDao.updateMember(id, updateClassMemberRequest)
        return ApiResponse(success = true, message = Localization.get("class_member_updated", lang))
    }

    override suspend fun deleteMember(
        id: Int,
        lang: String
    ): ApiResponse<Nothing> {
        classMemberDao.deleteMember(id)
        return ApiResponse(success = true, message = Localization.get("class_member_deleted", lang))
    }

}
