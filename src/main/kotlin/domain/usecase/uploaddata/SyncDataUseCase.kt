package domain.usecase.uploaddata

import base.mvi.DataState
import domain.repository.UploadDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SyncDataUseCase(
    private val uploadDataRepository: UploadDataRepository
) {

    suspend operator fun invoke() {
        uploadDataRepository.syncData()
    }
}

