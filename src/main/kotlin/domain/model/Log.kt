package domain.model

import domain.model.base.Entity
import domain.model.base.StringIdEntity
import io.realm.kotlin.types.RealmAny
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class Log(
    @PrimaryKey override var id: String,
    var dateTime: String,
    var type: LogTypeRealm? = null,
    var project: Project? = null,
    var student: Student? = null,
    var participation: Participation? = null,
    var source: LogSourceRealm? = null
):  RealmObject, StringIdEntity() {

    constructor() : this("", "")

    override fun members(): List<Any?> {
        return listOf(id, dateTime, type, project, student, participation)
    }

    override fun toString(): String {
        val subject = project ?: student ?: participation
        return "{" +
                "id=$id," +
                "dateTime=$dateTime," +
                "type=$type," +
                "subject=$subject," +
                "source=$source" +
                "}"
    }
}