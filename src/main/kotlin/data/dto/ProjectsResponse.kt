package ru.student.distribution.data.dto

import domain.model.Project

data class ProjectsResponse(
    val data: List<Project>,
    val projectCount: Int
)
