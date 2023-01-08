package domain.usecase.base

import kotlinx.coroutines.flow.Flow
import base.mvi.DataState

abstract class BaseFlowUseCase<T>() {
    abstract operator fun invoke(): Flow<DataState<T>>
}