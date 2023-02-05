package domain.usecase.student

import domain.model.Student
import domain.repository.StudentRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStudentsUseCase @Inject constructor(
    private val studentRepository: StudentRepository
) {

    operator fun invoke(): Flow<ResultsChange<Student>> = studentRepository.getStudents()
}