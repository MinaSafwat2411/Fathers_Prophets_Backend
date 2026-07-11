package com.fathersprophets.backend.database.repository.version

import com.fathersprophets.backend.database.dao.version.VersionDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.version.AdminPinRequest
import com.fathersprophets.backend.models.version.VersionRequest
import com.fathersprophets.backend.models.dto.VersionDto
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.PasswordUtil

class VersionRepository(
    private val versionDao: VersionDao
) : IVersionRepository {

    override suspend fun getLastVersion(lang: String): ApiResponse<VersionDto> {
        val lastVersion = versionDao.getLastVersion()?:
            throw IllegalArgumentException(Localization.get("version_not_found", lang))

        return ApiResponse(
            success = true,
            data = lastVersion,
            message = Localization.get("version_found", lang)
        )
    }

    override suspend fun addNewVersion(versionRequest: VersionRequest, lang: String): ApiResponse<Nothing> {
        val versionDto = versionRequest.toVersionDto().copy(
            adminPin = PasswordUtil.hashPassword(versionRequest.adminPin ?: "")
        )
        versionDao.addVersion(versionDto)

        return ApiResponse(success = true, message = Localization.get("version_added_successfully", lang))
    }

    override suspend fun onValidatePin(adminPinRequest: AdminPinRequest, lang: String): ApiResponse<Nothing> {
        val adminPin = versionDao.getPinByVersion(adminPinRequest.toVersionDto()) ?: throw IllegalArgumentException(
            Localization.get("version_not_found", lang)
        )
        val isMatch = adminPin.let {
            PasswordUtil.checkPassword(adminPinRequest.adminPin ?: "", it.adminPin)
        }
        val messageKey = if (isMatch) "pin_correct" else "pin_incorrect"
        return ApiResponse(success = isMatch, message = Localization.get(messageKey, lang))
    }

    override suspend fun changePinVersion(adminPinRequest: AdminPinRequest, lang: String): ApiResponse<Nothing> {
        val versionDto = adminPinRequest.toVersionDto().copy(
            adminPin = PasswordUtil.hashPassword(adminPinRequest.adminPin ?: "")
        )

        versionDao.changePinVersion(versionDto)

        return ApiResponse(success = true, message = Localization.get("pin_updated_successfully", lang))
    }
}
