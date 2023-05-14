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
    var subject: Project? = null,
    var source: LogSourceRealm? = null
):  RealmObject, StringIdEntity() {

    constructor() : this("", "")

    override fun members(): List<Any?> {
        return listOf(id, dateTime, type, subject)
    }

    override fun toString(): String {
        return "{" +
                "id=$id," +
                "dateTime=$dateTime," +
                "type=$type," +
                "subject=$subject," +
                "source=$source" +
                "}"
    }
}