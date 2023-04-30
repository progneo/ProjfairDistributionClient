package ui.distribution_algorithm.viewmodel

import base.mvi.BaseViewModel
import domain.usecase.department.GetDepartmentsUseCase
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.specialty.GetSpecialtiesUseCase
import domain.usecase.student.GetStudentsUseCase
import domain.usecase.supervisor.GetSupervisorsUseCase
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
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
    private val getSpecialtiesUseCase: GetSpecialtiesUseCase,
    private val getSupervisorsUseCase: GetSupervisorsUseCase,
): BaseViewModel<PreviewContract.Intent, PreviewContract.ScreenState>() {

    val students = MutableStateFlow<List<AlgorithmStudent>>(emptyList())
    val projects = MutableStateFlow<List<AlgorithmProject>>(emptyList())
    val participations = MutableStateFlow<List<AlgorithmParticipation>>(emptyList())
    val institutes = MutableStateFlow<List<AlgorithmInstitute>>(emptyList())
    val departments = MutableStateFlow<List<AlgorithmDepartment>>(emptyList())
    val supervisors = MutableStateFlow<List<AlgorithmSupervisor>>(emptyList())
    val specialties = MutableStateFlow<List<AlgorithmSpecialty>>(emptyList())

    init {
        getStudents()
        getProjects()
        getDepartments()
        getInstitutes()
        getSpecialties()
        getParticipations()
        getSupervisors()
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
                projects.value = it.list.map { p -> p.toAlgorithmModel() }
            }
        }
    }

    private fun getDepartments() {
        coroutineScope.launch {
            getDepartmentsUseCase().collect {
                departments.value = it.list.map { d -> d.toAlgorithmModel() }
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

    private fun getSpecialties() {
        coroutineScope.launch {
            getSpecialtiesUseCase().collect {
                specialties.value = it.list.map { s -> s.toAlgorithmModel() }
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

    private fun getSupervisors() {
        coroutineScope.launch {
            getSupervisorsUseCase().collect {
                supervisors.value = it.list.map { s -> s.toAlgorithmModel() }
            }
        }
    }

    override fun createInitialState(): PreviewContract.ScreenState {
        return PreviewContract.ScreenState.Idle
    }

    override fun handleIntent(intent: PreviewContract.Intent) {

    }
}