package data.local.entity

import org.jetbrains.exposed.sql.Table

object ProjectSpeciality: Table() {
    val id = integer("id")
    val projectId = integer("project_id").references(Project.id)
    val specialityId = integer("speciality_id").references(Speciality.id)

    override val primaryKey = PrimaryKey(id)
}