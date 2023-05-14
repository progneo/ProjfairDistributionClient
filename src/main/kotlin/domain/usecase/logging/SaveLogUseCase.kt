package domain.usecase.logging

import domain.model.Log
import domain.repository.LoggingRepository
import javax.inject.Inject

class SaveLogUseCase @Inject constructor(
    private val loggingRepository: LoggingRepository
) {

    suspend operator fun invoke(log: Log) {
        loggingRepository.saveLog(log)
    }
}