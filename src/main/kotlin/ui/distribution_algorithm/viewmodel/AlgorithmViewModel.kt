package ui.distribution_algorithm.viewmodel

import base.mvi.BaseViewModel
import domain.model.CleanProject
import domain.model.CleanProjectSpecialty
import domain.model.GeneratedDistribution
import domain.usecase.file.SaveGeneratedDistributionUseCase
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.student.GetStudentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.distribution_algorithm.common.*
import ui.preview.contract.PreviewContract
import javax.inject.Inject

class AlgorithmViewModel @Inject constructor(
    private val getStudentsUseCase: GetStudentsUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val getParticipationsUseCase: GetParticipationsUseCase,
    private val getInstitutesUseCase: GetInstitutesUseCase,
    private val saveGeneratedDistributionUseCase: SaveGeneratedDistributionUseCase
): BaseViewModel<PreviewContract.Intent, PreviewContract.ScreenState>() {

    val students = MutableStateFlow<List<AlgorithmStudent>>(emptyList())
    val projects = MutableStateFlow<List<AlgorithmProject>>(emptyList())
    val participations = MutableStateFlow<List<AlgorithmParticipation>>(emptyList())
    val institutes = MutableStateFlow<List<AlgorithmInstitute>>(emptyList())

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
                        department = project.department!!,
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

                    projectsSpecialties.forEach { psp ->
                        if (psp.course == null) {
                            val isNormal = psp.specialty.name.endsWith("б")
                            val count = if (isNormal) 2 else 3
                            (3 until (count+3)).forEach { number ->
                                newProjectSpecialties.add(
                                    psp.apply {
                                        course = number
                                    }
                                )
                            }
                        }
                    }

                    temp.add(
                        projectCopy.apply {
                            projectSpecialties = newProjectSpecialties
                        }
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


    fun saveStudentsByProjects(generatedDistribution: GeneratedDistribution) {
        saveGeneratedDistributionUseCase(generatedDistribution)
    }

    override fun createInitialState(): PreviewContract.ScreenState {
        return PreviewContract.ScreenState.Idle
    }

    override fun handleIntent(intent: PreviewContract.Intent) {

    }
}