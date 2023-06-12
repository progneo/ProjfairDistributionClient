package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import ui.details.participation.viewmodel.ParticipationDetailsViewModel.Companion.projectId

open class Participation(
    @PrimaryKey override var id: Int,
    var studentId: Int,
    var studentNumz: Int,
    var studentName: String,
    var projectId: Int,
    var priority: Int,
    var updatedAt: String,
    var state: Int
) : RealmObject, LoggingEntity() {

    constructor() : this(0, 0, 0, "", 0, 0, "", 0)

    override fun members(): List<Any?> {
        return listOf(id, studentId, projectId, priority, updatedAt, state)
    }

    override fun toLog(): String {
        return "Заявка - studentNumz: $studentNumz, projectId: $projectId"
    }

    override fun toString(): String {
        return "{" +
                "id=$id," +
                "studentId=$studentId," +
                "projectId=$projectId," +
                "priority=$priority" +
                "updatedAt=$updatedAt" +
                "state=$state" +
                "}"
    }
}

open class ParticipationSize(
    @PrimaryKey override var id: Int,
    var participationLastId: Int
) : RealmObject, LoggingEntity() {

    constructor() : this(0, 0)

    override fun members(): List<Any?> {
        return listOf(id, participationLastId)
    }
}
