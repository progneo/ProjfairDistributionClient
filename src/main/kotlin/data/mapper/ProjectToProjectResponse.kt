package data.mapper

import com.grapecity.documents.excel.drawing.b.it
import data.dto.*
import domain.model.Project
import io.realm.kotlin.ext.realmListOf

fun projectToProjectResponse(project: Project): ProjectResponse? {
    return null
//    return ProjectResponse(
//        id = project.id,
//        title = project.name,
//        places = project.places,
//        goal = project.goal ?: "",
//        difficulty = project.difficulty,
//        description = project.description ?: "",
//        dateStart = project.dateStart,
//        dateEnd = project.dateEnd,
//        customer = project.customer ?: "",
//        productResult = project.productResult,
//        studyResult = project.studyResult,
//        supervisors = project.supervisors.toList().map { supervisor ->
//            SupervisorResponse(
//                id = supervisor.id,
//                roles = supervisor.roles.toList().map { role ->
//                    SupervisorRoleResponse(
//                        id = role.id,
//                        name = role.name
//                    )
//                },
//                supervisor = SupervisorDetailsResponse(
//                    id = supervisor.id,
//                    fio = supervisor.name,
//                    department = DepartmentResponse(
//                        id = supervisor.department!!.id,
//                        name = supervisor.department!!.name,
//                        institute = InstituteResponse(
//                            id = supervisor.department!!.institute!!.id,
//                            name = supervisor.department!!.institute!!.name
//                        )
//                    )
//                )
//            )
//        },
//        department = departmentResponseToDepartment(project.department) ?: departmentResponseToDepartment(
//            try {
//                project.supervisors[0].supervisor.department
//            } catch (e: IndexOutOfBoundsException) {
//                null
//            }
//        ),
//        projectSpecialties = realmListOf(*project.projectSpecialities.map {
//            projectSpecialtyResponseToProjectSpecialty(it)
//        }.toTypedArray())
//    )
}