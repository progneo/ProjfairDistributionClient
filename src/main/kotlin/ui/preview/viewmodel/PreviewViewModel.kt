package ui.preview.viewmodel

import androidx.compose.runtime.mutableStateOf
import base.mvi.BaseViewModel
import domain.model.*
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.student.GetStudentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ui.filter.FilterType
import ui.preview.contract.PreviewContract
import ui.preview.widget.PreviewTabPage
import ui.preview.widget.StudentsTabPage
import ui.preview.widget.filter.InstituteFilterConfiguration
import ui.preview.widget.filter.ProjectFilterApplier
import ui.preview.widget.filter.StudentFilterApplier

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

    private val _students = MutableStateFlow<List<Student>>(emptyList())
    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    private val _participations = MutableStateFlow<List<Participation>>(emptyList())
    val institutes = MutableStateFlow<List<Institute>>(emptyList())
    val departments = MutableStateFlow<List<Department>>(emptyList())

    val filteredProjects = MutableStateFlow<List<Project>>(emptyList())

    private val _studentsWithParticipations = MutableStateFlow<List<Student>>(emptyList())
    private val _studentsWithoutParticipations = MutableStateFlow<List<Student>>(emptyList())

    private val _studentsIds = mutableSetOf<Int>()

    var previewTabPage = mutableStateOf(PreviewTabPage.Students)

    init {
        getStudents()
        getProjects()
        getParticipations()
    }

    fun getFilteredStudents(studentsTabPage: StudentsTabPage): StateFlow<List<Student>> {
        return when (studentsTabPage) {
            StudentsTabPage.Enrolled -> {
                _studentsWithParticipations
            }
            StudentsTabPage.Uncounted -> {
                _studentsWithoutParticipations
            }
        }
    }

    private fun getStudents() {
        coroutineScope.launch {
            getStudentsUseCase().collect {
                _students.value = it.list
                _studentsIds.addAll(it.list.map { stud -> stud.id })
            }
        }
    }

    private fun getProjects() {
        coroutineScope.launch {
            getProjectsUseCase().collect {
                _projects.value = it.list
            }
        }
    }

    private fun getParticipations() {
        coroutineScope.launch {
            getParticipationsUseCase().collect {
                _participations.value = it.list

                val set = it.list.map { p -> p.studentId }.toSet()
                val with = mutableListOf<Student>()
                val without = mutableListOf<Student>()

                _students.value.forEach { stud ->
                    if (set.contains(stud.id)) {
                        with.add(stud)
                    } else {
                        without.add(stud)
                    }
                }

                _studentsWithParticipations.value = with
                _studentsWithoutParticipations.value = without
            }
        }
    }

    fun getParticipationByStudent(studentId: Int): List<Participation> {
        return _participations.value.filter { part -> part.studentId == studentId }
    }

    fun getParticipationByProject(projectId: Int): List<Participation> {
        return _participations.value.filter { part -> part.projectId == projectId }.sortedBy { it.priority }
    }

    fun getStudentById(id: Int): Student? {
        return _students.value.find { it.id == id }
    }

    fun getProjectById(projectId: Int): Project? {
        _projects.value.forEach {
            println(it.id)
        }
        return _projects.value.find { proj -> proj.id == projectId }
    }

    fun filterStudents(instituteFilterConfiguration: InstituteFilterConfiguration) {
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
        val newStudentsWithParticipations = StudentFilterApplier.applyAndGet(
            students = _studentsWithParticipations.value,
            institute = institute,
            department = department
        )
        val newStudentsWithoutParticipations = StudentFilterApplier.applyAndGet(
            students = _studentsWithoutParticipations.value,
            institute = institute,
            department = department
        )
        _studentsWithParticipations.value = newStudentsWithParticipations
        _studentsWithoutParticipations.value = newStudentsWithoutParticipations
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
        val newProjects = ProjectFilterApplier.applyAndGet(
            projects = _projects.value,
            institute = institute,
            department = department
        )
        filteredProjects.value = newProjects
    }
}