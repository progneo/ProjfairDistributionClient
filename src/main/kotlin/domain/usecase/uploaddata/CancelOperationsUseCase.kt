package domain.usecase.uploaddata

import domain.repository.UploadDataRepository
import ui.uploaddata.viewmodel.UploadDataViewModel
import javax.inject.Inject

class CancelOperationsUseCase @Inject constructor(
    private val uploadDataRepository: UploadDataRepository
) {

    suspend operator fun invoke() {
        uploadDataRepository.stopOperations()
    }
}