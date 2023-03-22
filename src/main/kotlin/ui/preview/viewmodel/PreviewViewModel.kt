package ui.preview.viewmodel

import androidx.compose.runtime.mutableStateOf
import base.mvi.BaseViewModel
import domain.model.*
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.student.GetStudentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.filter.FilterType
import ui.preview.contract.PreviewContract
import ui.preview.widget.InstituteFilterConfiguration
import ui.preview.widget.PreviewTabPage
import ui.preview.widget.ProjectFilterApplier

class PreviewViewModel constructor(
    private val getStudentsUseCase: GetStudentsUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val getParticipationsUseCase: GetParticipationsUseCase,
) : BaseViewModel<PreviewContract.Intent, PreviewContract.ScreenState>() {

    override fun createInitialState(): PreviewContract.ScreenState {
        return PreviewContract.ScreenState.Idle
    }

    override fun handleIntent(intent: PreviewContract.Intent) {
        when (intent) {
            is PreviewContract.Intent.GetStudents -> getStudents()
            is PreviewContract.Intent.GetProjects -> getProjects()
        }
    }

    val students = MutableStateFlow<List<Student>>(emptyList())
    private val projects = MutableStateFlow<List<Project>>(emptyList())
    private val participations = MutableStateFlow<List<Participation>>(emptyList())
    val institutes = MutableStateFlow<List<Institute>>(emptyList())
    val departments = MutableStateFlow<List<Department>>(emptyList())

    val filteredProjects = MutableStateFlow<List<Project>>(emptyList())

    val studentsWithParticipations = MutableStateFlow<List<Student>>(emptyList())
    val studentsWithoutParticipations = MutableStateFlow<List<Student>>(emptyList())

    private val studentsIds = mutableSetOf<Int>()

    var previewTabPage = mutableStateOf(PreviewTabPage.Students)

    init {
        getStudents()
        getProjects()
        getParticipations()
    }

    private fun getStudents() {
        coroutineScope.launch {
            getStudentsUseCase().collect {
                students.value = it.list
                studentsIds.addAll(it.list.map { stud -> stud.id })
            }
        }
    }

    private fun getProjects() {
        coroutineScope.launch {
            getProjectsUseCase().collect {
                projects.value = it.list
            }
        }
    }

    private fun getParticipations() {
        coroutineScope.launch {
            getParticipationsUseCase().collect {
                participations.value = it.list

                val set = it.list.map { p -> p.studentId }.toSet()
                val with = mutableListOf<Student>()
                val without = mutableListOf<Student>()

                students.value.forEach { stud ->
                    if (set.contains(stud.id)) {
                        with.add(stud)
                    } else {
                        without.add(stud)
                    }
                }

                studentsWithParticipations.value = with
                studentsWithoutParticipations.value = without
            }
        }
    }

    fun getParticipationByStudent(studentId: Int): List<Participation> {
        return participations.value.filter { part -> part.studentId == studentId }
    }

    fun getProjectById(projectId: Int): Project? {
        projects.value.forEach {
            println(it.id)
        }
        return projects.value.find { proj -> proj.id == projectId }
    }

    fun filterProjects(instituteFilterConfiguration: InstituteFilterConfiguration) {
        val institute = try {
            instituteFilterConfiguration.filters[FilterType.INSTITUTE]!!.selectedValue.filterEntity as Institute
        } catch (e: ClassCastException) {
            null
        }
        val department = try {
            instituteFilterConfiguration.filters[FilterType.INSTITUTE]!!.selectedValue.filterEntity as Department
        } catch (e: ClassCastException) {
            null
        }
        val newProjects = ProjectFilterApplier(
            projects = projects.value,
            institute = institute,
            department = department
        ).applyAndGet()
        filteredProjects.value = newProjects
    }
}