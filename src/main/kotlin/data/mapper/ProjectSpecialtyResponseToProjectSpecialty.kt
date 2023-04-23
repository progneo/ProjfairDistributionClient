package data.mapper

import data.dto.ProjectSpecialtyResponse
import domain.model.ProjectSpecialty

fun projectSpecialtyResponseToProjectSpecialty(projectSpecialty: ProjectSpecialtyResponse): ProjectSpecialty {
    return ProjectSpecialty(
        id = projectSpecialty.id,
        course = projectSpecialty.course,
        priority = projectSpecialty.priority,
        specialty = specialtyResponseToSpecialty(projectSpecialty.specialty)
    )
}