package data.dto

data class SpecialtyResponse(
    val id: Int,
    val name: String,
    val institute: InstituteResponse,
    val department: DepartmentResponse
)