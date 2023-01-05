package ru.student.distribution.data.dto

import ru.student.distribution.domain.model.Project

data class ProjectsResponse(
    val data: List<Project>,
    val projectCount: Int
)
