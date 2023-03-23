package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class ProjectSpecialty(
    @PrimaryKey override var id: Int,
    var projectId: Int,
    var specialityId: Int
): Entity(), RealmObject {

    constructor() : this(0, 0, 0)

    override fun members(): List<Any?> {
        return listOf(id, projectId, specialityId)
    }
}
