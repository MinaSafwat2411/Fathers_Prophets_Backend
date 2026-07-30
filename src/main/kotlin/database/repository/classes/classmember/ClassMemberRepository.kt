package com.fathersprophets.backend.database.repository.classes.classmember

import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.classmember.ClassMemberResponse
import com.fathersprophets.backend.models.classmember.UpdateClassMemberRequest
import com.fathersprophets.backend.utils.Localization

class ClassMemberRepository(
    private val classMemberDao: ClassMemberDao,
) : IClassMemberRepository {

    override suspend fun findMemberClass(
        classId: Int,
        lang: String
    ): ApiResponse<List<ClassMemberResponse>> {

        val members = classMemberDao.findMemberClass(classId)

        return ApiResponse(
            success = true,
            data = members.map { it.toClassMemberResponse() },
            message = Localization.get("class_members_found", lang)
        )
    }

    override suspend fun findMyClassMembers(
        userId: Int,
        lang: String
    ): ApiResponse<List<ClassMemberResponse>> {

        val classMember = classMemberDao.findByUserId(userId)
            ?: throw NotFoundException(Localization.get("user_has_no_class", lang))

        return findMemberClass(classMember.classId, lang)
    }

    override suspend fun addMember(
        addClassMemberRequest: AddClassMemberRequest,
        lang: String
    ): ApiResponse<ClassMemberResponse> {

        val created = classMemberDao.addMember(addClassMemberRequest.toClassMemberDto())
            ?:throw IllegalArgumentException(Localization.get("can_not_add_class_member", lang))

        return ApiResponse(
            success = true,
            data = created.toClassMemberResponse(),
            message = Localization.get("class_member_added", lang)
        )
    }

    override suspend fun updateMember(
        id: Int,
        updateClassMemberRequest: UpdateClassMemberRequest,
        lang: String
    ): ApiResponse<ClassMemberResponse> {

        val updated = classMemberDao.updateMember(updateClassMemberRequest.toClassMemberDto(id))
            ?: throw IllegalArgumentException(Localization.get("class_member_not_updated", lang))



        return ApiResponse(
            success = true,
            data = updated.toClassMemberResponse(),
            message = Localization.get("class_member_updated", lang)
        )
    }

    override suspend fun deleteMember(
        id: Int,
        lang: String
    ): ApiResponse<Nothing> {

        val deleted = classMemberDao.deleteMember(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("class_member_not_deleted", lang))


        return ApiResponse(success = true, message = Localization.get("class_member_deleted", lang))
    }
}
