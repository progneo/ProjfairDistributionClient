package data.dto

import com.google.gson.annotations.SerializedName

data class SpecialtyResponse(
    val id: Int,
    val name: String,
    @SerializedName("institute") val institute: InstituteResponse,
    @SerializedName("department") val department: DepartmentResponse
)