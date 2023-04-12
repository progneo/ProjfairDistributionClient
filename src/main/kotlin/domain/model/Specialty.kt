package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class Specialty(
    @PrimaryKey override var id: Int,
    var name: String,
    var institute: Institute? = null,
    var department: Department? = null
): Entity(), RealmObject {

    constructor() : this(0, "", null, null)

    override fun members(): List<Any?> {
        return listOf(id, name, institute, department)
    }
}
