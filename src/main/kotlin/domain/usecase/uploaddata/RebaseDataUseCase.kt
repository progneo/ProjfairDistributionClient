package domain.usecase.uploaddata

import domain.repository.UploadDataRepository

class RebaseDataUseCase(
    private val uploadDataRepository: UploadDataRepository,
) {

    suspend operator fun invoke() {
        uploadDataRepository.rebaseData()
    }
}

