package data.dto

import com.google.gson.annotations.SerializedName

data class ProjectSpecialtyResponse(
    val id: Int,
    val course: Int?,
    val priority: Int?,
    @SerializedName("speciality") val specialty: SpecialtyResponse,
)