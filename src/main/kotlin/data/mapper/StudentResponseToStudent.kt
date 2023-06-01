package data.mapper

import data.dto.StudentResponse
import domain.model.Student

fun studentResponseToStudent(student: StudentResponse): Student {
    val id = student.numz.toIntOrNull() ?: 0
    return Student(
        id = student.id,
        numz = id,
        name = student.fio,
        course = student.course,
        group = student.trainingGroup,
        shouldDistribute = true,
        specialty = specialtyResponseToSpecialty(student.specialty)
    )
    //TODO: set specialtyId
}