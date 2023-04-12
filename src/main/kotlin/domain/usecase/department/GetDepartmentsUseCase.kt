package domain.usecase.department

import domain.model.Department
import domain.repository.DepartmentRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDepartmentsUseCase @Inject constructor(
    private val departmentRepository: DepartmentRepository
) {

    operator fun invoke(): Flow<ResultsChange<Department>> = departmentRepository.getDepartments()
}