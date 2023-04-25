package data.mapper

import data.dto.SupervisorRoleResponse
import domain.model.SupervisorRole

fun supervisorRoleResponseToSupervisorRole(supervisorRole: SupervisorRoleResponse): SupervisorRole {
    return SupervisorRole(
        id = supervisorRole.id,
        name = supervisorRole.name
    )
}