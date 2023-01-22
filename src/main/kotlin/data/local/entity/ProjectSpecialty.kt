package data.local.entity

import org.jetbrains.exposed.sql.Table

object ProjectSpecialty: Table() {
    val id = integer("id")
    val projectId = integer("project_id").references(Project.id)
    val specialityId = integer("speciality_id").references(Specialty.id)

    override val primaryKey = PrimaryKey(id)
}