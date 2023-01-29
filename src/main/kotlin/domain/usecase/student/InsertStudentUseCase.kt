package domain.usecase.student

import data.repository.StudentRepositoryImpl
import domain.model.Student
import javax.inject.Inject

class InsertStudentUseCase @Inject constructor(
    private val studentRepositoryImpl: StudentRepositoryImpl
) {

    suspend operator fun invoke(student: Student) {
        studentRepositoryImpl.insertStudent(student)
    }
}