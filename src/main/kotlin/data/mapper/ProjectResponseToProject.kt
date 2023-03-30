package data.mapper

import data.dto.ProjectResponse
import domain.model.Project

fun projectResponseToProject(project: ProjectResponse): Project {
    return Project(
        id = project.id,
        name = project.title,
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
        supervisors = project.supervisors,
        //department = Department(0, "name", Institute(id = 0, name = "name"))
    )
}