package domain.usecase.specialty

import domain.model.Specialty
import domain.repository.SpecialtyRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpecialtiesUseCase @Inject constructor(
    private val specialtyRepository: SpecialtyRepository,
) {

    operator fun invoke(): Flow<ResultsChange<Specialty>> = specialtyRepository.getSpecialties()
}