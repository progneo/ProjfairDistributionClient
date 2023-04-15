package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import ui.filter.FilterEntity

open class Institute(
    @PrimaryKey override var id: Int,
    override var name: String
): Entity(), RealmObject, FilterEntity {

    constructor() : this(0, "")

    override fun members(): List<Any?> {
        return listOf(id, name)
    }

    object Base: Institute() {
        override var name: String = "Все"
    }
}