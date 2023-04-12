package data.mapper

import data.dto.DepartmentResponse
import domain.model.Department

fun departmentResponseToDepartment(department: DepartmentResponse?): Department? {
    if (department == null) return null
    return Department(
        id = department.id,
        name = department.name,
        institute = instituteResponseToInstitute(department.institute)
    )
}