package data.dto

data class SupervisorResponse(
    val id: Int,
    val roles: List<SupervisorRoleResponse>,
    val supervisor: SupervisorDetailsResponse
)
