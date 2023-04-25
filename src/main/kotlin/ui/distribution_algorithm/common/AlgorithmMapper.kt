package ui.distribution_algorithm.common

import domain.model.*

typealias AlgorithmStudent = ru.student.distribution.data.model.Student
typealias AlgorithmProject = ru.student.distribution.data.model.Project
typealias AlgorithmParticipation = ru.student.distribution.data.model.Participation
typealias AlgorithmSpecialty = ru.student.distribution.data.model.Specialty
typealias AlgorithmSupervisor = ru.student.distribution.data.model.Supervisor

fun Student.toAlgorithmModel(): AlgorithmStudent {
    return AlgorithmStudent(
        id = this.id,
        name = this.name,
        group = this.group.substring(0, group.indexOfFirst { it == '-' }),
        realGroup = this.group
    )
}

fun Project.toAlgorithmModel(groups: List<String>): AlgorithmProject {
    return AlgorithmProject(
        id = this.id,
        title = this.name,
        places = this.places,
        freePlaces = this.places,
        groups = groups,
        difficulty = this.difficulty,
        customer = this.customer ?: "",
        //supervisors = this.supervisors TODO: update model
        supervisors = listOf()
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
        name = this.name
    )
}

fun Supervisor.toAlgorithmModel(): AlgorithmSupervisor {
    return AlgorithmSupervisor(
        id = this.id,
        name = this.name
    )
}