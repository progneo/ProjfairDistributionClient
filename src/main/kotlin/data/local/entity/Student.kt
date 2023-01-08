package data.local.entity

import org.jetbrains.exposed.sql.Table

object Student: Table() {
    val id = integer("id")
    val name = varchar("name", 100)
    val group = varchar("group", 10)
    val shouldDistribute = bool("should_distribute")
    val specialityId = integer("speciality_id").references(Speciality.id)

    override val primaryKey = PrimaryKey(id)
}