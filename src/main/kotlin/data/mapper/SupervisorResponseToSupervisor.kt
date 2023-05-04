package data.mapper

import data.dto.SupervisorResponse
import domain.model.Supervisor
import io.realm.kotlin.ext.realmListOf

fun supervisorResponseToSupervisor(supervisor: SupervisorResponse): Supervisor {
    return Supervisor(
        id = supervisor.supervisor.id,
        roles = realmListOf(*supervisor.roles.map { supervisorRoleResponseToSupervisorRole(it) }.toTypedArray()),
        name = supervisor.supervisor.fio,
        department = departmentResponseToDepartment(supervisor.supervisor.department),
    )
}