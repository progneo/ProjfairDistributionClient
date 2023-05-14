package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class Log(
    @PrimaryKey override var id: Int,
    var dateTime: String,
    var logType: LogTypeRealm? = null,
    var subject: Any? = null
): Entity(), RealmObject {

    constructor() : this(0, "", null, null)

    override fun members(): List<Any?> {
        return listOf(id, dateTime, logType, subject)
    }

    override fun toString(): String {
        return "{" +
                "id=$id," +
                "dateTime=$dateTime," +
                "logType=$logType," +
                "subject=$subject" +
                "}"
    }
}