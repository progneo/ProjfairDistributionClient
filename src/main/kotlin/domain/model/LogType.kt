package domain.model

import com.grapecity.documents.excel.drawing.b.it
import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlin.math.log

enum class LogType {
    SAVE,
    REMOVE
}

open class LogTypeRealm(
    @PrimaryKey override val id: Int = 0,
) : RealmObject, Entity() {

    constructor() : this(0)

    private var _logType: String = LogType.SAVE.name

    var logType: LogType
        get() = LogType.values().first() { it.name == _logType }
        set(value) {
            _logType = value.name
        }

    override fun members(): List<Any?> {
        return listOf(id, _logType)
    }

    override fun toString(): String {
        return "{" +
                "id=$id," +
                "logType=$_logType" +
                "}"
    }
}