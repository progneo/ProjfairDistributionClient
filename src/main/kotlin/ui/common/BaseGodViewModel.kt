package ui.common

import base.mvi.BaseViewModel
import domain.model.*
import domain.usecase.department.GetDepartmentsUseCase
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.project.UpdateProjectUseCase
import domain.usecase.specialty.GetSpecialtiesUseCase
import domain.usecase.student.GetStudentsUseCase
import domain.usecase.supervisor.GetSupervisorsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ui.filter.FilterEntity
import ui.filter.FilterType
import ui.preview.contract.PreviewContract
import ui.preview.widget.StudentsTabPage
import ui.preview.widget.filter.InstituteFilterConfiguration
import ui.preview.widget.filter.ProjectFilterApplier
import ui.preview.widget.filter.StudentFilterApplier

open class BaseGodViewModel(
    private val getStudentsUseCase: GetStudentsUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val getParticipationsUseCase: GetParticipationsUseCase,
    private val getInstitutesUseCase: GetInstitutesUseCase,
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
    private val getSpecialtiesUseCase: GetSpecialtiesUseCase,
    private val getSupervisorsUseCase: GetSupervisorsUseCase,
) : BaseViewModel<PreviewContract.Intent, PreviewContract.ScreenState>() {

    override fun createInitialState(): PreviewContract.ScreenState {
        return PreviewContract.ScreenState.Idle
    }

    override fun handleIntent(intent: PreviewContract.Intent) {}

    private val _students = MutableStateFlow<List<Student>>(emptyList())
    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    private val _participations = MutableStateFlow<List<Participation>>(emptyList())
    private val _institutes = MutableStateFlow<List<Institute>>(emptyList())
    private val _departments = MutableStateFlow<List<Department>>(emptyList())
    private val _supervisors = MutableStateFlow<List<Supervisor>>(emptyList())

    val specialties = MutableStateFlow<List<Specialty>>(emptyList())
    val filteredDepartments = MutableStateFlow<List<Department>>(emptyList())
    val institutes = MutableStateFlow<List<Institute>>(emptyList())

    private val _filteredProjectsByDepartments = MutableStateFlow<List<Project>>(emptyList())
    val filteredProjects = MutableStateFlow<List<Project>>(emptyList())
    val filteredSupervisors = MutableStateFlow<List<Supervisor>>(emptyList())

    private val _studentsWithParticipations = MutableStateFlow<List<Student>>(emptyList())
    private val _filteredStudentsWithParticipationsByDepartments = MutableStateFlow<List<Student>>(emptyList())
    private val _filteredStudentsWithParticipations = MutableStateFlow<List<Student>>(emptyList())
    private val _studentsWithoutParticipations = MutableStateFlow<List<Student>>(emptyList())
    private val _filteredStudentsWithoutParticipationsByDepartments = MutableStateFlow<List<Student>>(emptyList())
    private val _filteredStudentsWithoutParticipations = MutableStateFlow<List<Student>>(emptyList())

    val studentFilterConfiguration =
        MutableStateFlow<InstituteFilterConfiguration>(InstituteFilterConfiguration(Institute.Base, Department.Base))

    val projectFilterConfiguration =
        MutableStateFlow<InstituteFilterConfiguration>(InstituteFilterConfiguration(Institute.Base, Department.Base))

    private var lastSearchStudentString = ""
    private var lastSearchProjectString = ""

    init {
        getStudents()
        getProjects()
        getParticipations()
        getInstitutes()
        getDepartments()
        getSpecialties()
        getSupervisors()
    }

    fun getFilteredStudents(studentsTabPage: StudentsTabPage): StateFlow<List<Student>> {
        return when (studentsTabPage) {
            StudentsTabPage.Enrolled -> {
                _filteredStudentsWithParticipations
            }

            StudentsTabPage.Uncounted -> {
                _filteredStudentsWithoutParticipations
            }
        }
    }

    private fun getStudents() {
        coroutineScope.launch {
            getStudentsUseCase().collect {
                _students.value = it.list
            }
        }
    }

    private fun getProjects() {
        coroutineScope.launch {
            getProjectsUseCase().collect {
                _projects.value = it.list
                _filteredProjectsByDepartments.value = it.list
                filteredProjects.value = it.list
            }
        }
    }

    fun updateProject(project: Project) {
        coroutineScope.launch {
            updateProjectUseCase(project)
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

    private fun getSpecialties() {
        coroutineScope.launch {
            getSpecialtiesUseCase().collect {
                specialties.value = it.list
            }
        }
    }

    private fun getParticipations() {
        coroutineScope.launch {
            getParticipationsUseCase().collect { it ->
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

                _studentsWithParticipations.value = with.sortedWith(compareBy { student -> student.name })
                _filteredStudentsWithParticipationsByDepartments.value = _studentsWithParticipations.value
                _filteredStudentsWithParticipations.value = _studentsWithParticipations.value

                _studentsWithoutParticipations.value = without.sortedWith(compareBy { student -> student.name })
                _filteredStudentsWithoutParticipationsByDepartments.value = _studentsWithoutParticipations.value
                _filteredStudentsWithoutParticipations.value = _studentsWithoutParticipations.value
            }
        }
    }

    private fun getSupervisors() {
        coroutineScope.launch {
            getSupervisorsUseCase().collect {
                _supervisors.value = it.list
            }
        }
    }

    fun getFilteredSupervisors(department: Department): List<Supervisor> {
        return _supervisors.value.filter { s ->
            s.department != null && s.department!!.id == department.id
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
        return _projects.value.find { proj -> proj.id == projectId }
    }

    fun filterStudentsByString(searchString: String) {
        val search = searchString.lowercase()
        _filteredStudentsWithParticipations.value = _filteredStudentsWithParticipationsByDepartments.value.filter { student ->
            println(student)
            student.name.lowercase().contains(search) ||
                    student.group.lowercase().contains(search) ||
                    student.numz.toString().lowercase().contains(search) ||
                    student.specialty!!.name.lowercase().contains(search)
        }
        _filteredStudentsWithoutParticipations.value = _filteredStudentsWithoutParticipationsByDepartments.value.filter { student ->
            student.name.lowercase().contains(search) ||
                    student.group.lowercase().contains(search) ||
                    student.numz.toString().lowercase().contains(search) ||
                    student.specialty!!.name.lowercase().contains(search)
        }
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
        _filteredStudentsWithParticipationsByDepartments.value = newStudentsWithParticipations
        _filteredStudentsWithoutParticipationsByDepartments.value = newStudentsWithoutParticipations
        filterStudentsByString(lastSearchStudentString)
    }

    fun filterProjectsByString(searchString: String) {
        val search = searchString.lowercase()
        lastSearchProjectString = search
        filteredProjects.value = _filteredProjectsByDepartments.value.filter { project ->
            project.name.lowercase().contains(search) ||
                    project.department?.name?.lowercase()?.contains(search) == true ||
                    project.department?.institute?.name?.lowercase()?.contains(search) == true ||
                    project.supervisors.toString().lowercase().contains(search) ||
                    project.customer.lowercase().contains(search)
        }
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
        _filteredProjectsByDepartments.value = newProjects
        filterProjectsByString(lastSearchProjectString)
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