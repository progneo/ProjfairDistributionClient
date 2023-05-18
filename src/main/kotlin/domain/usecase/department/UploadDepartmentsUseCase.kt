package domain.usecase.department

import domain.repository.DepartmentRepository
import javax.inject.Inject

class UploadDepartmentsUseCase @Inject constructor(
    private val departmentRepository: DepartmentRepository
) {

    suspend operator fun invoke() {
        departmentRepository.uploadDepartments()
    }
}