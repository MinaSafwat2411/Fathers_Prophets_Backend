package com.fathersprophets.backend.database.repository.version

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.version.AdminPinRequest
import com.fathersprophets.backend.models.version.VersionRequest
import com.fathersprophets.backend.models.dto.VersionDto

interface IVersionRepository {
    suspend fun getLastVersion(lang : String) : ApiResponse<VersionDto>
    suspend fun addNewVersion(versionRequest: VersionRequest,lang : String) : ApiResponse<Nothing>
    suspend fun onValidatePin(adminPinRequest: AdminPinRequest,lang : String) : ApiResponse<Nothing>

    suspend fun changePinVersion(adminPinRequest: AdminPinRequest,lang : String) : ApiResponse<Nothing>
}