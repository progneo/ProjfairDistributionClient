package data.mapper

import data.dto.SpecialtyResponse
import domain.model.Specialty

fun specialtyResponseToSpecialty(specialty: SpecialtyResponse?): Specialty? {
    if (specialty == null) return null
    return Specialty(
        id = specialty.id,
        name = specialty.name,
        institute = instituteResponseToInstitute(specialty.institute),
        department = departmentResponseToDepartment(specialty.department)
    )
}