package domain.model.base

import io.realm.kotlin.types.RealmObject

abstract class Entity : RealmObject, EqualsAndHashCode() {
    open val id: Int = 0
}

abstract class StringIdEntity: RealmObject, EqualsAndHashCode() {
    open val id: String = ""
}