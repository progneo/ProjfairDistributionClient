package ui.distribution_algorithm.common

import domain.model.*

typealias AlgorithmStudent = ru.student.distribution.model.Student
typealias AlgorithmProject = ru.student.distribution.model.Project
typealias AlgorithmParticipation = ru.student.distribution.model.Participation
typealias AlgorithmSpecialty = ru.student.distribution.model.Specialty
typealias AlgorithmSupervisor = ru.student.distribution.model.Supervisor
typealias AlgorithmDepartment = ru.student.distribution.model.Department
typealias AlgorithmInstitute = ru.student.distribution.model.Institute
typealias AlgorithmProjectSpecialty = ru.student.distribution.model.ProjectSpecialty

fun Student.toAlgorithmModel(): AlgorithmStudent {
    return AlgorithmStudent(
        id = this.id,
        name = this.name,
        groupFamily = this.group.substring(0, group.indexOfFirst { it == '-' }),
        fullGroupName = this.group,
        specialty = this.specialty!!.toAlgorithmModel(),
        course = 1,
        numz = this.numz
    )
}

fun CleanProject.toAlgorithmModel(): AlgorithmProject {
    return AlgorithmProject(
        id = this.id,
        title = this.name,
        places = this.places,
        freePlaces = this.places,
        busyPlaces = 0,
        groups = this.projectSpecialties.map { it.specialty.toAlgorithmModel() }.toSet().toList(),
        difficulty = this.difficulty,
        customer = this.customer,
        supervisors = this.supervisors.map { it.toAlgorithmModel() },
        department = try {
            this.supervisors.first().department!!.toAlgorithmModel()
        } catch (e: Exception) {
            AlgorithmDepartment(-1, "-1", AlgorithmInstitute(-1, "-1"))
        },
        projectSpecialties = this.projectSpecialties.map { it.toAlgorithmModel() }
    )
}

fun Participation.toAlgorithmModel(): AlgorithmParticipation {
    return AlgorithmParticipation(
        id = this.id,
        priority = this.priority,
        projectId = this.projectId,
        studentId = this.studentId,
        stateId = 0
    )
}

fun Specialty.toAlgorithmModel(): AlgorithmSpecialty {
    return AlgorithmSpecialty(
        id = this.id,
        name = this.name,
        institute = this.institute!!.toAlgorithmModel(),
        department = this.department!!.toAlgorithmModel()
    )
}

fun Supervisor.toAlgorithmModel(): AlgorithmSupervisor {
    return AlgorithmSupervisor(
        id = this.id,
        name = this.name,
        department = this.department!!.toAlgorithmModel(),
        position = ""
    )
}

fun Department.toAlgorithmModel(): AlgorithmDepartment {
    return AlgorithmDepartment(
        id = this.id,
        name = this.name,
        institute = this.institute!!.toAlgorithmModel()
    )
}

fun Institute.toAlgorithmModel(): AlgorithmInstitute {
    return AlgorithmInstitute(
        id = this.id,
        name = this.name,
    )
}

fun CleanProjectSpecialty.toAlgorithmModel(): AlgorithmProjectSpecialty {
    return AlgorithmProjectSpecialty(
        id = this.id,
        course = this.course!!,
        specialty = this.specialty.toAlgorithmModel(),
        priority = 1
    )
}