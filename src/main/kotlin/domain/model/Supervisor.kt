package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class Supervisor(
    @PrimaryKey override var id: Int,
    var name: String
): Entity(), RealmObject {

    constructor() : this(0, "")

    override fun members(): List<Any?> {
        return listOf(id, name)
    }
}
