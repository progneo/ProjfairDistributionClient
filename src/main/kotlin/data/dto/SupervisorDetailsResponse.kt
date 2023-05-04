package data.dto

data class SupervisorDetailsResponse(
    val id: Int,
    val fio: String,
    val department: DepartmentResponse,
)
