package domain.usecase.uploaddata

import domain.usecase.base.BaseFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.student.distribution.core.base.DataState
import ru.student.distribution.domain.repository.UploadDataRepository
import javax.inject.Inject

class UploadExceptionalStudentsUseCase @Inject constructor(
    private val uploadDataRepository: UploadDataRepository
): BaseFlowUseCase<Boolean>() {

    override operator fun invoke(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            val response = uploadDataRepository.uploadExceptionalStudents()
            emit(DataState.Success(response))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}