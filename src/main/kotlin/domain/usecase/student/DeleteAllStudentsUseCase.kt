package domain.usecase.student

import di.Review
import domain.repository.StudentRepository
import javax.inject.Inject

class DeleteAllStudentsUseCase @Inject constructor(
    @Review private val studentRepository: StudentRepository
) {

    suspend operator fun invoke() {
        studentRepository.deleteAllStudents()
    }
}