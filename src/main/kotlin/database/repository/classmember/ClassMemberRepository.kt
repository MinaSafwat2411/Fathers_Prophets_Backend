package com.fathersprophets.backend.database.repository.classmember

import com.fathersprophets.backend.database.dao.classes.ClassMemberDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.classmember.ClassMemberResponse
import com.fathersprophets.backend.models.classmember.UpdateClassMemberRequest
import com.fathersprophets.backend.models.dto.ClassMemberDto
import com.fathersprophets.backend.utils.Localization

class ClassMemberRepository(
    private val classMemberDao: ClassMemberDao,
) : IClassMemberRepository {

    override suspend fun findMemberClass(
        classId: Int,
        lang: String
    ): ApiResponse<List<ClassMemberResponse>> {

        val classDto = idToClass(classId)

        val members = classMemberDao.findMemberClass(classDto).map {
            it.toClassMemberResponse()
        }

        if (members.isEmpty()) throw IllegalArgumentException(Localization.get("class_members_not_found", lang))

        return ApiResponse(success = true, data = members, message = Localization.get("class_members_found", lang))
    }

    override suspend fun addMember(
        addClassMemberRequest: AddClassMemberRequest,
        lang: String
    ): ApiResponse<ClassMemberResponse> {

        val id = classMemberDao.addMember(addClassMemberRequest.toClassMemberDto())

        val classMemberDto = classMemberDao.findById(
            idToClass(id)
        ) ?: throw IllegalArgumentException(Localization.get("can_not_add_class_member", lang))

        return ApiResponse(
            success = true,
            data = classMemberDto.toClassMemberResponse(),
            message = Localization.get("class_member_added", lang)
        )
    }

    override suspend fun updateMember(
        id: Int,
        updateClassMemberRequest: UpdateClassMemberRequest,
        lang: String
    ): ApiResponse<ClassMemberResponse> {

        classMemberDao.updateMember(updateClassMemberRequest.toClassMemberDto(id))

        val classMemberDto = updateClassMemberRequest.toClassMemberDto(id)

        return ApiResponse(
            success = true,
            data = classMemberDto.toClassMemberResponse(),
            message = Localization.get("class_member_updated", lang)
        )
    }

    override suspend fun deleteMember(
        id: Int,
        lang: String
    ): ApiResponse<Nothing> {

        val classMemberDto = idToClass(id)

        classMemberDao.deleteMember(classMemberDto)

        return ApiResponse(success = true, message = Localization.get("class_member_deleted", lang))
    }

    private fun idToClass(id: Int): ClassMemberDto {
        return ClassMemberDto(
            id = id,
            name = "",
            image = "",
            isTeacher = false,
            classId = 0,
            userId = 0
        )
    }

}
