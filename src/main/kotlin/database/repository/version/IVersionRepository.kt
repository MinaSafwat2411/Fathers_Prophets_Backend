package com.fathersprophets.backend.database.repository.version

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.version.AdminPinRequest
import com.fathersprophets.backend.models.version.VersionRequest
import com.fathersprophets.backend.database.dto.version.VersionDto

interface IVersionRepository {
    fun getLastVersion(lang : String) : ApiResponse<VersionDto>
    fun addNewVersion(versionRequest: VersionRequest,lang : String) : ApiResponse<Nothing>
    fun onValidatePin(adminPinRequest: AdminPinRequest,lang : String) : ApiResponse<Nothing>

    fun changePinVersion(adminPinRequest: AdminPinRequest,lang : String) : ApiResponse<Nothing>
}