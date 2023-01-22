package data.local.entity

import org.jetbrains.exposed.sql.Table

object Participation: Table() {
    val id = integer("id")
    val studentId = integer("student_id").references(Student.id)
    val projectId = integer("project_id").references(Project.id)
    val priority = integer("priority")
    val stateId = integer("state_id")

    override val primaryKey = PrimaryKey(id)
}