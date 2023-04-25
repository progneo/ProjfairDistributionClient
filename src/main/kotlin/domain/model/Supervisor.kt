package domain.model

import domain.model.base.Entity
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import ui.filter.FilterEntity

open class Supervisor(
    @PrimaryKey override var id: Int,
    var roles: RealmList<SupervisorRole> = realmListOf(),
    override var name: String,
    var department: Department? = null,
    var position: String
): Entity(), RealmObject, FilterEntity {

    constructor() : this(
        id = 0,
        name = "",
        position = ""
    )

    override fun members(): List<Any?> {
        return listOf(id, name)
    }
}
