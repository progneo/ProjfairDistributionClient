package domain.usecase.student

import domain.repository.ParticipationRepository
import domain.repository.ProjectRepository
import domain.repository.StudentRepository
import javax.inject.Inject

class SyncStudentsUseCase(
    private val studentRepository: StudentRepository
) {

    suspend operator fun invoke() {
        studentRepository.syncStudents()
    }
}