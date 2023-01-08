package data.local.entity

import org.jetbrains.exposed.sql.Table

object ProjectSupervisor: Table() {
    val id = integer("id")
    val projectId = integer("project_id").references(Project.id)
    val supervisorId = integer("supervisor_id").references(Supervisor.id)

    override val primaryKey = PrimaryKey(id)
}