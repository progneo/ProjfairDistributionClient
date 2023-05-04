package ui.preview.widget.filter

import domain.model.Department
import domain.model.Institute
import domain.model.Student

object StudentFilterApplier {

    fun applyAndGet(
        students: List<Student>,
        institute: Institute?,
        department: Department?
    ): List<Student> {
        if (institute == null) return students
        else if (department == null) {
            return students.filter { stud ->
                stud.specialty?.institute?.id == institute.id
            }
        }
        return students.filter { stud ->
            stud.specialty?.department?.id == department.id
        }
    }
}