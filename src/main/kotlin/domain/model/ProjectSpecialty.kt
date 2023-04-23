package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class ProjectSpecialty(
    @PrimaryKey override var id: Int,
    var course: Int? = null,
    var specialty: Specialty? = null,
    var priority: Int? = null
): Entity(), RealmObject {

    constructor() : this(0, null, null, null)

    override fun members(): List<Any?> {
        return listOf(id, course, specialty, priority)
    }
}
