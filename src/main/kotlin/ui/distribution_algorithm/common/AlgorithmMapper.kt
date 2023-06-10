package ui.distribution_algorithm.common

import domain.model.*
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList

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
        course = this.course,
        numz = this.numz
    )
}

fun AlgorithmStudent.fromAlgorithmModel(): Student {
    return Student(
        id = this.id,
        name = this.name,
        specialty = this.specialty.fromAlgorithmModel(),
        course = this.course,
        numz = this.numz,
        shouldDistribute = true,
        group = this.fullGroupName
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
        department = this.department.toAlgorithmModel(),
        projectSpecialties = this.projectSpecialties.filter { it.priority == 1 }.map { it.toAlgorithmModel() },
        anotherProjectSpecialties = this.projectSpecialties.filter { it.priority != 1 }.map { it.toAlgorithmModel() },
        goal = this.goal,
        description = this.description,
        dateStart = this.dateStart,
        dateEnd = this.dateEnd,
        productResult = this.productResult,
        studyResult = this.studyResult
    )
}

fun AlgorithmProject.fromAlgorithmModel(): Project {
    return Project(
        id = this.id,
        name = this.title,
        places = this.places,
        freePlaces = this.places,
        difficulty = this.difficulty,
        customer = this.customer,
        supervisors = realmListOf(*this.supervisors.map { it.fromAlgorithmModel() }.toTypedArray()),
        department = this.department.fromAlgorithmModel(),
        projectSpecialties = realmListOf(
            *(
                    this.projectSpecialties.map { it.fromAlgorithmModel() } +
                            this.anotherProjectSpecialties.map { it.fromAlgorithmModel() }
                    ).toTypedArray()
        ),
        goal = this.goal,
        description = this.description,
        dateStart = this.dateStart,
        dateEnd = this.dateEnd,
        productResult = this.productResult,
        studyResult = this.studyResult
    )
}

fun Participation.toAlgorithmModel(): AlgorithmParticipation {
    return AlgorithmParticipation(
        id = this.id,
        priority = this.priority,
        projectId = this.projectId,
        studentId = this.studentId,
        stateId = 0,
        studentNumz = this.studentNumz,
        studentName = this.studentName,
        updatedAt = this.updatedAt
    )
}

fun AlgorithmParticipation.fromAlgorithmModel(): Participation {
    return Participation(
        id = this.id,
        priority = this.priority,
        projectId = this.projectId,
        studentId = this.studentId,
        studentNumz = this.studentNumz,
        studentName = this.studentName,
        updatedAt = this.updatedAt
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

fun AlgorithmSpecialty.fromAlgorithmModel(): Specialty {
    return Specialty(
        id = this.id,
        name = this.name,
        institute = this.institute.fromAlgorithmModel(),
        department = this.department.fromAlgorithmModel()
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

fun AlgorithmSupervisor.fromAlgorithmModel(): Supervisor {
    return Supervisor(
        id = this.id,
        name = this.name,
        department = this.department.fromAlgorithmModel(),
        roles = realmListOf()
    )
}

fun Department.toAlgorithmModel(): AlgorithmDepartment {
    return AlgorithmDepartment(
        id = this.id,
        name = this.name,
        institute = this.institute!!.toAlgorithmModel()
    )
}

fun AlgorithmDepartment.fromAlgorithmModel(): Department {
    return Department(
        id = this.id,
        name = this.name,
        institute = this.institute.fromAlgorithmModel()
    )
}

fun Institute.toAlgorithmModel(): AlgorithmInstitute {
    return AlgorithmInstitute(
        id = this.id,
        name = this.name,
    )
}

fun AlgorithmInstitute.fromAlgorithmModel(): Institute {
    return Institute(
        id = this.id,
        name = this.name,
    )
}

fun CleanProjectSpecialty.toAlgorithmModel(): AlgorithmProjectSpecialty {
    return AlgorithmProjectSpecialty(
        id = this.id,
        course = this.course!!,
        specialty = this.specialty.toAlgorithmModel(),
        priority = this.priority!!
    )
}

fun AlgorithmProjectSpecialty.fromAlgorithmModel(): ProjectSpecialty {
    return ProjectSpecialty(
        id = this.id,
        course = this.course,
        specialty = this.specialty.fromAlgorithmModel(),
        priority = this.priority
    )
}