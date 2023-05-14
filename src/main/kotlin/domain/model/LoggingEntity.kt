package domain.model

import domain.model.base.Entity
import domain.model.base.StringIdEntity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

abstract class LoggingEntity: RealmObject, Entity() {

    abstract fun toLog(): String
}