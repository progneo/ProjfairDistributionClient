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
                //TODO: add institute filter
                1==1
                //proj.department?.institute?.id == institute.id
            }
        }
        return students.filter { stud ->
            //TODO: add department filter
            1==1
            //proj.department?.id == department.id
        }
    }
}