package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlin.math.log

enum class LogType {
    SAVE,
    REMOVE,
    CHANGE
}

open class LogTypeRealm(
    @PrimaryKey override var id: Int = 0,
    var logType: String
) : RealmObject, Entity() {

    constructor() : this(0, "")

    var logTypeEnum: LogType
        get() = LogType.values().first { it.name == logType }
        set(value) {
            logType = value.name
        }

    override fun members(): List<Any?> {
        return listOf(id, logType)
    }

    override fun toString(): String {
        return "{" +
                "id=$id," +
                "logType=$logType" +
                "}"
    }
}