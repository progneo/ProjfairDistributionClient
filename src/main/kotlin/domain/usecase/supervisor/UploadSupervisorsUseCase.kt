package domain.usecase.supervisor

import domain.repository.InstituteRepository
import domain.repository.SupervisorRepository
import javax.inject.Inject

class UploadSupervisorsUseCase @Inject constructor(
    private val supervisorRepository: SupervisorRepository
) {

    suspend operator fun invoke() {
        supervisorRepository.uploadSupervisors()
    }
}