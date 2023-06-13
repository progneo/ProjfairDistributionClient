package domain.usecase.uploaddata

import domain.repository.UploadDataRepository
import ui.uploaddata.viewmodel.UploadDataViewModel
import javax.inject.Inject

class CancelOperationsUseCase(
    private val uploadDataRepository: UploadDataRepository
) {

    operator fun invoke() {
        uploadDataRepository.stopOperations()
    }
}