package data.local.entity

import org.jetbrains.exposed.sql.Table

object GeneratedDistribution: Table() {
    val id = integer("id")
    val projects = largeText("projects", eagerLoading = true)
    val participations = largeText("participations", eagerLoading = true)

    override val primaryKey = PrimaryKey(id)
}