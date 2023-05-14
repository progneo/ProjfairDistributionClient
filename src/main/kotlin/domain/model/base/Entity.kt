package domain.model.base

import io.realm.kotlin.types.RealmObject

abstract class Entity : RealmObject, EqualsAndHashCode() {
    open var id: Int = 0
}

abstract class StringIdEntity: RealmObject, EqualsAndHashCode() {
    open var id: String = ""
}