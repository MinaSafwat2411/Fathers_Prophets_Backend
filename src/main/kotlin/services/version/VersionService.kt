package com.fathersprophets.backend.services.version

import com.fathersprophets.backend.database.repository.version.IVersionRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.version.AdminPinRequest
import com.fathersprophets.backend.models.dto.version.VersionRequest
import com.fathersprophets.backend.models.dto.version.VersionResponse
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class VersionService(
    private val versionRepository: IVersionRepository
) : IVersionService {
    override suspend fun getLastVersion(lang: String): ApiResponse<VersionResponse> {
        return versionRepository.getLastVersion(lang)
    }

    override suspend fun addNewVersion(
        versionRequest: VersionRequest,
        lang: String
    ): ApiResponse<Nothing> {
        validateRequired(
            versionRequest.version to "version",
            versionRequest.adminPin to "admin_pin",
            lang = lang
        )
        return versionRepository.addNewVersion(versionRequest, lang)
    }

    override suspend fun getPinByVersion(
        adminPinRequest: AdminPinRequest,
        lang: String
    ): ApiResponse<Nothing> {
        validateRequired(
            adminPinRequest.version to "version",
            adminPinRequest.adminPin to "admin_pin",
            lang = lang
        )
        return versionRepository.getPinByVersion(adminPinRequest, lang)
    }

    override suspend fun changePinVersion(
        adminPinRequest: AdminPinRequest,
        lang: String
    ): ApiResponse<Nothing> {
        validateRequired(
            adminPinRequest.version to "version",
            adminPinRequest.adminPin to "admin_pin",
            lang = lang
        )
        return versionRepository.changePinVersion(adminPinRequest, lang)
    }
}
