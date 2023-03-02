package data.mapper

import data.dto.ProjectResponse
import domain.model.Project

fun projectResponseToResponse(project: ProjectResponse): Project {
    return Project(
        id = project.id,
        title = project.title,
        places = project.places,
        freePlaces = project.places,
        goal = project.goal,
        difficulty = project.difficulty,
        description = project.description,
        dateStart = project.dateStart,
        dateEnd = project.dateEnd,
        customer = project.customer,
        productResult = project.productResult,
        studyResult = project.studyResult,
        supervisors = project.supervisors
    )
}