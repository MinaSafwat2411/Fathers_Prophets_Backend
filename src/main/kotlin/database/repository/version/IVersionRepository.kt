package com.fathersprophets.backend.database.repository.version

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.version.AdminPinRequest
import com.fathersprophets.backend.models.dto.version.VersionRequest
import com.fathersprophets.backend.models.dto.version.VersionResponse

interface IVersionRepository {
    fun getLastVersion(lang : String) : ApiResponse<VersionResponse>
    fun addNewVersion(versionRequest: VersionRequest,lang : String) : ApiResponse<Nothing>
    fun getPinByVersion(adminPinRequest: AdminPinRequest,lang : String) : ApiResponse<Nothing>

    fun changePinVersion(adminPinRequest: AdminPinRequest,lang : String) : ApiResponse<Nothing>
}