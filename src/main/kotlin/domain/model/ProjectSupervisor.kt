package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class ProjectSupervisor(
    @PrimaryKey override var id: Int,
    var projectId: Int,
    var supervisorId: Int
): Entity(), RealmObject {

    constructor() : this(0, 0, 0)

    override fun members(): List<Any?> {
        return listOf(id, projectId, supervisorId)
    }
}
