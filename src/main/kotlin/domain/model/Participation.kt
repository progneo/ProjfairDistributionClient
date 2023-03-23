package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class Participation(
    @PrimaryKey override var id: Int,
    var studentId: Int,
    var projectId: Int,
    var priority: Int,
) : Entity(), RealmObject {

    constructor() : this(0, 0, 0, 0)

    override fun members(): List<Any?> {
        return listOf(id, studentId, projectId, priority)
    }
}
