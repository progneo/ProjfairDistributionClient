package data.mapper

import data.dto.StudentResponse
import domain.model.Student

fun studentResponseToStudent(student: StudentResponse): Student {
    val id = student.numz.toIntOrNull() ?: 0
    return Student(
        //id = student.numz.toInt(),
        id = student.id,
        numz = id,
        name = student.fio,
        group = student.trainingGroup,
        shouldDistribute = true,
        specialtyId = 0
    )
    //TODO: set specialtyId
}