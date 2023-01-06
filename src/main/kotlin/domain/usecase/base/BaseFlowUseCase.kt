package domain.usecase.base

import kotlinx.coroutines.flow.Flow
import ru.student.distribution.core.base.DataState

abstract class BaseFlowUseCase<T>() {
    abstract operator fun invoke(): Flow<DataState<T>>
}