package domain.usecase.institute

import domain.repository.InstituteRepository
import javax.inject.Inject

class UploadInstitutesUseCase @Inject constructor(
    private val instituteRepository: InstituteRepository
) {

    suspend operator fun invoke() {
        instituteRepository.uploadInstitutes()
    }
}