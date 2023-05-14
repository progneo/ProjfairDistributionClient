package domain.model

import common.logging.LoggingEntity
import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class Participation(
    @PrimaryKey override var id: Int,
    var studentId: Int,
    var studentNumz: Int,
    var projectId: Int,
    var priority: Int,
) : Entity(), RealmObject, LoggingEntity {

    constructor() : this(0, 0, 0, 0, 0)

    override fun members(): List<Any?> {
        return listOf(id, studentId, projectId, priority)
    }

    override fun toLog(): String {
        return "studentNumz: $studentNumz, projectId: $projectId"
    }

    override fun toString(): String {
        return "{" +
                "id=$id," +
                "studentId=$studentId," +
                "projectId=$projectId," +
                "priority=$priority" +
                "}"
    }
}
