package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import ui.filter.FilterEntity

open class Student(
    @PrimaryKey override var id: Int,
    var numz: Int,
    override var name: String,
    var group: String,
    var shouldDistribute: Boolean,
    var specialtyId: Int,
) : Entity(), RealmObject, FilterEntity {

    constructor() : this(0, 0, "", "", true, 0)

    override fun members(): List<Any?> {
        return listOf(id, numz, name, group, shouldDistribute, specialtyId)
    }
}
