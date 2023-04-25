package data.mapper

import data.dto.SupervisorDetailsResponse
import domain.model.Supervisor
import io.realm.kotlin.ext.realmListOf

fun supervisorDetailsResponseToSupervisor(supervisor: SupervisorDetailsResponse): Supervisor {
    return Supervisor(
        id = supervisor.id,
        roles = realmListOf(),
        name = supervisor.fio,
        department = departmentResponseToDepartment(supervisor.department),
        position = supervisor.position
    )
}