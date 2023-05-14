package domain.model

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
    var logSource: String
) : RealmObject, Entity() {

    constructor() : this(0, "")

    override fun members(): List<Any?> {
        return listOf(id, logSource)
    }

    override fun toString(): String {
        return "{" +
                "id=$id," +
                "logSource=$logSource" +
                "}"
    }
}