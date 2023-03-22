package ui.preview.widget

import domain.model.Department
import domain.model.Institute
import domain.model.Project

class ProjectFilterApplier(
    val projects: List<Project>,
    val institute: Institute?,
    val department: Department?
) {

    fun applyAndGet(): List<Project> {
        if (institute == null) return projects
        else if (department == null) {
            return projects.filter { proj ->
                1==1
                //proj.department?.institute?.id == institute.id
            }
        }
        return projects.filter { proj->
            1==1
            //proj.department?.id == department.id
        }
    }
}