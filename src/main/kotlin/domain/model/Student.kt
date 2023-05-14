package domain.model

import common.logging.LoggingEntity
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
    var specialty: Specialty? = null
) : Entity(), RealmObject, FilterEntity, LoggingEntity {

    constructor() : this(0, 0, "", "", true, null)

    override fun members(): List<Any?> {
        return listOf(id, numz, name, group, shouldDistribute, specialty)
    }

    override fun toLog(): String {
        return "numz: $numz, name: $name"
    }

    override fun toString(): String {
        return "{" +
                "id=$id," +
                "numz=$numz," +
                "group=$group," +
                "specialty=$specialty" +
                "}"
    }
}
