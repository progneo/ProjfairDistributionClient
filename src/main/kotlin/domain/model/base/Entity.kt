package domain.model.base

import io.realm.kotlin.types.RealmObject

abstract class Entity : RealmObject, EqualsAndHashCode() {
    open val id: Int = 0
}