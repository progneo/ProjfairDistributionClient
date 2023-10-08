package ui.details.participation.viewmodel

import base.mvi.BaseViewModel
import domain.model.Participation
import domain.model.Project
import domain.model.Student
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.participation.UpdateParticipationUseCase
import domain.usecase.student.GetStudentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ParticipationDetailsViewModel @Inject constructor(
    private val getParticipationsUseCase: GetParticipationsUseCase,
    private val getStudentsUseCase: GetStudentsUseCase,
    private val updateParticipationUseCase: UpdateParticipationUseCase,
) : BaseViewModel() {

    companion object {
        var projectId: Int? = null
    }

    val selectedProjectStudents = MutableStateFlow<MutableList<Student>>(mutableListOf())
    val selectedChooseStudents = MutableStateFlow<MutableList<Student>>(mutableListOf())
    val projectParticipation = MutableStateFlow<MutableList<Participation>>(mutableListOf())
    val requiredParticipation = MutableStateFlow<MutableList<Participation>>(mutableListOf())
    var currentProject: Project? = null

    private var allStudents: List<Student> = emptyList()
    val outStudents = MutableStateFlow<MutableList<Student>>(mutableListOf())

    init {
        getStudents()
    }

    private fun getStudents() {
        coroutineScope.launch {
            getStudentsUseCase().collect {
                allStudents = it.list
                setParticipation()
            }
        }
    }

    private fun setParticipation() {
        coroutineScope.launch {
            getParticipationsUseCase().collect {
                projectParticipation.value = it.list.filter { part ->
                    part.projectId == projectId
                }.sortedBy { part ->
                    part.priority
                }.toMutableList()
                requiredParticipation.value = it.list.toMutableList()

                val set = it.list.map { p -> p.studentId }.toSet()
                val with = mutableListOf<Student>()
                val without = mutableListOf<Student>()

                allStudents.forEach { stud ->
                    if (set.contains(stud.id)) {
                        with.add(stud)
                    } else {
                        without.add(stud)
                    }
                }

                setOutStudents(without)
            }
        }
    }

    fun setProjectParticipation(participation: List<Participation>) {
        projectParticipation.value =
            participation.sortedWith(compareBy({ it.priority }, { it.studentName })).toMutableList()
    }

    fun setRequiredParticipation(participation: List<Participation>) {
        requiredParticipation.value =
            participation.sortedWith(compareBy({ it.priority }, { it.studentName })).toMutableList()
    }

    fun setOutStudents(students: List<Student>) {
        outStudents.value = students.sortedBy { it.name }.toMutableList()
    }

    fun clearSelectedProjectStudents() {
        selectedProjectStudents.value = mutableListOf()
    }

    fun clearSelectedChooseStudents() {
        selectedChooseStudents.value = mutableListOf()
    }

    fun updateParticipation() {
        coroutineScope.launch {
            updateParticipationUseCase(projectParticipation.value)
        }
    }
}
