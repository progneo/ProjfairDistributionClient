package domain.usecase.uploaddata

import base.mvi.DataState
import domain.repository.UploadDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RebaseDataUseCase @Inject constructor(
    private val uploadDataRepository: UploadDataRepository
) {

    operator fun invoke(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            println("before trying")
            val response = uploadDataRepository.rebaseData()
            println("$response")
            emit(DataState.Success(response))
        } catch (e: Exception) {
            println(e)
            emit(DataState.Error(e))
        }
    }
}

