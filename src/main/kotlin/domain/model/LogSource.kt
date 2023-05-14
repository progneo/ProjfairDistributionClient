package domain.model

import com.grapecity.documents.excel.drawing.b.it
import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlin.math.log

enum class LogSource {
    USER,
    SERVER
}

open class LogSourceRealm(
    @PrimaryKey override var id: Int = 0,
    private var _logSource: String = LogSource.SERVER.name
) : RealmObject, Entity() {

    constructor() : this(0, "")

    var logSource: LogSource
        get() = LogSource.values().first() { it.name == _logSource }
        set(value) {
            _logSource = value.name
        }

    override fun members(): List<Any?> {
        return listOf(id, _logSource)
    }

    override fun toString(): String {
        return "{" +
                "id=$id," +
                "logSource=$_logSource" +
                "}"
    }
}