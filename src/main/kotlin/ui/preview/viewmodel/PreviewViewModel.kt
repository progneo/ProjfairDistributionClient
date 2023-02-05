package ui.preview.viewmodel

import base.mvi.BaseViewModel
import domain.model.Project
import domain.model.Student
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.student.GetStudentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.preview.contract.PreviewContract
import javax.inject.Inject

class PreviewViewModel @Inject constructor(
    private val getStudentsUseCase: GetStudentsUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
): BaseViewModel<PreviewContract.Intent, PreviewContract.ScreenState>() {

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

    init {
        getStudents()
        getProjects()
    }

    private fun getStudents() {
        coroutineScope.launch {
            getStudentsUseCase().collect {
                students.value = it.list
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
}