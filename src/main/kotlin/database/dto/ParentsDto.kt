package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.users.ParentsResponse

data class ParentsDto(
    val motherPhone : String? = null,
    val fatherPhone : String? = null,
){
    fun convertToParentsResponse() = ParentsResponse(
        motherPhone = motherPhone,
        fatherPhone = fatherPhone
    )
}
