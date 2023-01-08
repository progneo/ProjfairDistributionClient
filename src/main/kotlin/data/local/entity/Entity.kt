package data.local.entity

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

abstract class Entity: Table() {
    abstract val id: Column<Int>
}