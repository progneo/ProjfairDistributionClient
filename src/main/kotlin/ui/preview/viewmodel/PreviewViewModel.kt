package ui.preview.viewmodel

import androidx.compose.runtime.mutableStateOf
import base.mvi.BaseViewModel
import domain.model.*
import domain.usecase.department.GetDepartmentsUseCase
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.student.GetStudentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ui.filter.FilterEntity
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
    private val getInstitutesUseCase: GetInstitutesUseCase,
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
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
    private val _institutes = MutableStateFlow<List<Institute>>(emptyList())
    private val _departments = MutableStateFlow<List<Department>>(emptyList())
    val filteredDepartments = MutableStateFlow<List<Department>>(emptyList())
    val institutes = MutableStateFlow<List<Institute>>(emptyList())

    val filteredProjects = MutableStateFlow<List<Project>>(emptyList())

    private val _studentsWithParticipations = MutableStateFlow<List<Student>>(emptyList())
    private val _studentsWithoutParticipations = MutableStateFlow<List<Student>>(emptyList())

    private val _studentsIds = mutableSetOf<Int>()

    var previewTabPage = mutableStateOf(PreviewTabPage.Students)

    init {
        getStudents()
        getProjects()
        getParticipations()
        getInstitutes()
        getDepartments()
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
                filteredProjects.value = it.list
            }
        }
    }

    private fun getDepartments() {
        coroutineScope.launch {
            getDepartmentsUseCase().collect {
                _departments.value = it.list
                filteredDepartments.value = it.list
            }
        }
    }

    fun filterDepartments(institute: Institute?) {
        if (institute == null) {
            filteredDepartments.value = listOf(Department.Base) + _departments.value
            return
        }
        filteredDepartments.value = listOf(Department.Base) + _departments.value.filter { dep ->
            dep.institute?.id == institute.id
        }
    }

    private fun getInstitutes() {
        coroutineScope.launch {
            getInstitutesUseCase().collect {
                _institutes.value = it.list
                institutes.value = listOf(Institute.Base) + it.list
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
        val institute =
            if (instituteFilterConfiguration.selectedInstitute is Institute.Base) null
            else instituteFilterConfiguration.selectedInstitute
        val department =
            if (instituteFilterConfiguration.selectedDepartment is Department.Base) null
            else instituteFilterConfiguration.selectedDepartment

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
        val institute =
            if (instituteFilterConfiguration.selectedInstitute is Institute.Base) null
            else instituteFilterConfiguration.selectedInstitute
        val department =
            if (instituteFilterConfiguration.selectedDepartment is Department.Base) null
            else instituteFilterConfiguration.selectedDepartment

        val newProjects = ProjectFilterApplier.applyAndGet(
            projects = _projects.value,
            institute = institute,
            department = department
        )
        filteredProjects.value = newProjects
    }

    fun getValuesByType(
        filterType: FilterType,
        institute: Institute? = null,
        department: Department? = null,
        project: Project? = null,
    ): List<FilterEntity> {
        return when (filterType) {
            FilterType.INSTITUTE -> {
                _institutes.value
            }

            FilterType.DEPARTMENT -> {
                require(institute != null)
                _departments.value.filter { it.institute == institute }
            }

            FilterType.PROJECT -> {
                require(department != null)
                _projects.value.filter { proj ->
                    proj.department != null && proj.department!!.id == department.id
                }
            }

            FilterType.STUDENT -> {
                require(project != null)
                val studentsParticipations = _participations.value.filter { part ->
                    part.projectId == project.id
                }.map { part ->
                    part.studentId
                }.sorted()

                if (studentsParticipations.isEmpty()) return emptyList()

                val resultStudents = mutableListOf<Student>()
                var idIndex = 0

                for (stud in _students.value) {
                    if (studentsParticipations[idIndex] == stud.id) {
                        resultStudents.add(stud)
                        idIndex++
                        if (idIndex > studentsParticipations.lastIndex) break
                    }
                }

                resultStudents
            }
        }
    }
}