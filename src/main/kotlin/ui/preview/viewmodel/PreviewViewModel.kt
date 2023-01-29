package ui.preview.viewmodel

import domain.model.Student
import domain.usecase.student.GetStudentsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PreviewViewModel @Inject constructor(
    private val getStudentsUseCase: GetStudentsUseCase
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val students = MutableStateFlow<List<Student>>(emptyList())

    init {
        getStudents()
    }

    private fun getStudents() {
        coroutineScope.launch {
            println("LAUNCHED")
            getStudentsUseCase().collect {
                println("COLLECTED $it")
                students.value = it
            }
        }
    }
}