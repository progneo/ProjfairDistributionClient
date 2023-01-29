package domain.usecase.uploaddata

import base.mvi.DataState
import domain.repository.UploadDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class UploadExceptionalStudentsUseCase @Inject constructor(
    private val uploadDataRepository: UploadDataRepository,
) {

    operator fun invoke(exceptionalStudentsFile: File): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            val response = uploadDataRepository.uploadExceptionalStudents(exceptionalStudentsFile)
            println(response)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            println(e)
            emit(DataState.Error(e))
        }
    }
}

