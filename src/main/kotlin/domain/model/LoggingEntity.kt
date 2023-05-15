package domain.model

import domain.model.base.Entity
import domain.model.base.StringIdEntity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

open class LoggingEntity: RealmObject, Entity() {

    open fun toLog(): String {
        return ""
    }
    override fun members(): List<Any?> {
        return emptyList()
    }
}