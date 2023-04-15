package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import ui.filter.FilterEntity

open class Department(
    @PrimaryKey override var id: Int,
    override var name: String,
    var institute: Institute? = null
): Entity(), RealmObject, FilterEntity {

    constructor() : this(0, "", null)

    override fun members(): List<Any?> {
        return listOf(id, name, institute)
    }

    object Base: Department() {
        override var name: String = "Все"
    }
}