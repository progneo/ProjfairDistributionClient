package ui.distribution_algorithm.viewmodel

import base.mvi.BaseViewModel
import domain.model.*
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.participation.DeleteAllParticipationsUseCase
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.participation.InsertParticipationsUseCase
import domain.usecase.project.DeleteAllProjectsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.project.InsertProjectsUseCase
import domain.usecase.student.DeleteAllStudentsUseCase
import domain.usecase.student.GetStudentsUseCase
import domain.usecase.student.InsertStudentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.student.distribution.model.DistributionResults
import ui.distribution_algorithm.common.*
import javax.inject.Inject

class AlgorithmViewModel(
    private val getStudentsUseCase: GetStudentsUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val getParticipationsUseCase: GetParticipationsUseCase,
    private val getInstitutesUseCase: GetInstitutesUseCase,
    private val insertStudentUseCase: InsertStudentUseCase,
    private val insertProjectsUseCase: InsertProjectsUseCase,
    private val insertParticipationsUseCase: InsertParticipationsUseCase,
    private val deleteAllStudentsUseCase: DeleteAllStudentsUseCase,
    private val deleteAllProjectsUseCase: DeleteAllProjectsUseCase,
    private val deleteAllParticipationsUseCase: DeleteAllParticipationsUseCase
): BaseViewModel() {

    val students = MutableStateFlow<List<AlgorithmStudent>>(emptyList())
    val projects = MutableStateFlow<List<AlgorithmProject>>(emptyList())
    val participations = MutableStateFlow<List<AlgorithmParticipation>>(emptyList())
    val institutes = MutableStateFlow<List<AlgorithmInstitute>>(emptyList())

    var distributionResults: DistributionResults? = null

    init {
        getStudents()
        getProjects()
        getInstitutes()
        getParticipations()
    }

    private fun getStudents() {
        coroutineScope.launch {
            getStudentsUseCase().collect {
                students.value = it.list.map { s -> s.toAlgorithmModel() }
            }
        }
    }

    private fun getProjects() {
        coroutineScope.launch {
            getProjectsUseCase().collect {
                val temp = mutableListOf<CleanProject>()
                it.list.forEach { project ->
                    val projectCopy = CleanProject(
                        id = project.id,
                        name = project.name,
                        places = project.places,
                        freePlaces = project.freePlaces,
                        goal = project.goal,
                        description = project.description,
                        difficulty = project.difficulty,
                        dateStart = project.dateStart,
                        dateEnd = project.dateEnd,
                        customer = project.customer,
                        productResult = project.productResult,
                        studyResult = project.studyResult,
                        department = project.department ?: Department(id = -1, name = "-1", institute = Institute(-1, "-1")),
                        supervisors = project.supervisors.toList(),
                        projectSpecialties = project.projectSpecialties.map { oldPsp ->
                            CleanProjectSpecialty(
                                id = oldPsp.id,
                                course = oldPsp.course,
                                specialty = oldPsp.specialty!!,
                                priority = oldPsp.priority
                            )
                        }
                    )
                    val projectsSpecialties = projectCopy.projectSpecialties
                    val newProjectSpecialties = mutableListOf<CleanProjectSpecialty>()

                    projectsSpecialties.forEach psp@ { psp ->
                        if (psp.course == null) {
                            val isNormal = psp.specialty.name.endsWith("б")
                            val count = if (isNormal) 2 else 3
                            (3 until (count+3)).forEach { number ->
                                if (psp.priority == null) {
                                    (1..2).forEach { pr ->
                                        newProjectSpecialties.add(
                                            psp.copy(
                                                course = number,
                                                priority = pr
                                            )
                                        )
                                    }
                                } else {
                                    newProjectSpecialties.add(
                                        psp.copy(
                                            course = number
                                        )
                                    )
                                }
                            }
                        } else {
                            newProjectSpecialties.add(psp)
                        }
                    }

                    temp.add(
                        projectCopy.copy(
                            projectSpecialties = newProjectSpecialties
                        )
                    )
                }
                projects.value = temp.map { p -> p.toAlgorithmModel() }
            }
        }
    }

    private fun getInstitutes() {
        coroutineScope.launch {
            getInstitutesUseCase().collect {
                institutes.value = it.list.map { i -> i.toAlgorithmModel() }
            }
        }
    }

    private fun getParticipations() {
        coroutineScope.launch {
            getParticipationsUseCase().collect {
                participations.value = it.list.map { p -> p.toAlgorithmModel() }
            }
        }
    }

    private fun insertStudents() {
        coroutineScope.launch {
            deleteAllStudentsUseCase()
            insertStudentUseCase(students.value.map { it.fromAlgorithmModel() })
        }
    }

    private fun insertProjects() {
        coroutineScope.launch {
            deleteAllProjectsUseCase()
            insertProjectsUseCase(projects.value.map { it.fromAlgorithmModel() })
        }
    }

    private fun insertParticipation(participation: List<AlgorithmParticipation>) {
        coroutineScope.launch {
            deleteAllParticipationsUseCase()
            insertParticipationsUseCase(participation.map { it.fromAlgorithmModel() })
        }
    }

    fun insertNewParticipation(participation: List<AlgorithmParticipation>) {
        insertStudents()
        insertProjects()
        insertParticipation(participation)
    }
}