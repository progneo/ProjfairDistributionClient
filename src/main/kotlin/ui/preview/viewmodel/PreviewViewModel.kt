package ui.preview.viewmodel

import base.mvi.BaseViewModel
import domain.model.Participation
import domain.model.Project
import domain.model.Student
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.student.GetStudentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.preview.contract.PreviewContract
import javax.inject.Inject

class PreviewViewModel @Inject constructor(
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
    val projects = MutableStateFlow<List<Project>>(emptyList())
    val participations = MutableStateFlow<List<Participation>>(emptyList())

    val studentsWithParticipations = MutableStateFlow<List<Student>>(emptyList())
    val studentsWithoutParticipations = MutableStateFlow<List<Student>>(emptyList())

    private val studentsIds = mutableSetOf<Int>()

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

                println("total with = ${with.size}")
                println("total without = ${without.size}")

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
}