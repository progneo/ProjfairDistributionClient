package domain.usecase.uploaddata

import base.mvi.DataState
import domain.repository.UploadDataRepository
import domain.usecase.file.RemoveGeneratedDistributionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RebaseDataUseCase @Inject constructor(
    private val uploadDataRepository: UploadDataRepository,
    private val removeGeneratedDistributionUseCase: RemoveGeneratedDistributionUseCase
) {

    operator fun invoke(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            val response = uploadDataRepository.rebaseData()
            removeGeneratedDistributionUseCase()
            emit(DataState.Success(response))
        } catch (e: Exception) {
            println(e)
            emit(DataState.Error(e))
        }
    }
}

