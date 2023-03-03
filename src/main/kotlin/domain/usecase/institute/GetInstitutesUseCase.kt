package domain.usecase.institute

import domain.model.Institute
import domain.repository.InstituteRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInstitutesUseCase @Inject constructor(
    private val instituteRepository: InstituteRepository
) {

    operator fun invoke(): Flow<ResultsChange<Institute>> = instituteRepository.getInstitutes()
}