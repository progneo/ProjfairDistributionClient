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
): Entity(), RealmObject, FilterEntity {

    constructor() : this(
        id = 0,
        name = "",
    )

    override fun members(): List<Any?> {
        return listOf(id, name)
    }

    override fun toString(): String {
        return "{" +
                "id=$id," +
                "name=$name," +
                "department=$department" +
                "}"
    }
}
