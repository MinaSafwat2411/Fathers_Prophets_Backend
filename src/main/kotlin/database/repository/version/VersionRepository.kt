package com.fathersprophets.backend.database.repository.version

import com.fathersprophets.backend.database.dao.VersionDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.version.AdminPinRequest
import com.fathersprophets.backend.models.dto.version.VersionRequest
import com.fathersprophets.backend.models.dto.version.VersionResponse
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.PasswordUtil

class VersionRepository(
    private val versionDao: VersionDao
) : IVersionRepository {
    override suspend fun getLastVersion(lang: String): ApiResponse<VersionResponse> {
        val lastVersion = versionDao.getLastVersion()
        return ApiResponse(
            success = true,
            data = lastVersion,
            message = Localization.get("version_found", lang)
        )
    }

    override suspend fun addNewVersion(versionRequest: VersionRequest, lang: String): ApiResponse<Nothing> {
        versionDao.addVersion(versionRequest.copy(
            adminPin = PasswordUtil.hashPassword(versionRequest.adminPin!!)
        ))
        return ApiResponse(success = true, message = Localization.get("version_added_successfully", lang))
    }

    override suspend fun getPinByVersion(adminPinRequest: AdminPinRequest, lang: String): ApiResponse<Nothing> {
        val adminPin = versionDao.getPinByVersion(adminPinRequest.version!!)
        val isMatch = adminPin?.let {
            PasswordUtil.checkPassword(adminPinRequest.adminPin!!, it.adminPin)
        } ?: false
        val messageKey = if (isMatch) "pin_correct" else "pin_incorrect"
        return ApiResponse(success = isMatch, message = Localization.get(messageKey, lang))
    }

    override suspend fun changePinVersion(adminPinRequest: AdminPinRequest, lang: String): ApiResponse<Nothing> {
        versionDao.changePinVersion(adminPinRequest.copy(
            adminPin = PasswordUtil.hashPassword(adminPinRequest.adminPin!!)
        ))
        return ApiResponse(success = true, message = Localization.get("pin_updated_successfully", lang))
    }
}
