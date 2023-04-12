package data.dto

data class DepartmentResponse(
    val id: Int,
    val name: String,
    val institute: InstituteResponse
)