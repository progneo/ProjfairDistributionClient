package domain.usecase.logging

import domain.model.Log
import domain.repository.LoggingRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLogsUseCase @Inject constructor(
    private val loggingRepository: LoggingRepository
) {

    operator fun invoke(): Flow<ResultsChange<Log>> = loggingRepository.getLogs()
}