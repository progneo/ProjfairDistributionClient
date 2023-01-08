package domain.usecase.uploaddata

import domain.usecase.base.BaseFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import base.mvi.DataState
import ru.student.distribution.domain.repository.UploadDataRepository
import javax.inject.Inject

class SyncDataUseCase @Inject constructor(
    private val uploadDataRepository: UploadDataRepository
): BaseFlowUseCase<Boolean>() {

    override operator fun invoke(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            val response = uploadDataRepository.syncData()
            emit(DataState.Success(response))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}

