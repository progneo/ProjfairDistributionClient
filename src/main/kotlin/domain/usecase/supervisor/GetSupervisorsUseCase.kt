package domain.usecase.supervisor

import domain.model.Supervisor
import domain.repository.SupervisorRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSupervisorsUseCase @Inject constructor(
    private val supervisorRepository: SupervisorRepository
) {

    operator fun invoke(): Flow<ResultsChange<Supervisor>> = supervisorRepository.getSupervisors()
}