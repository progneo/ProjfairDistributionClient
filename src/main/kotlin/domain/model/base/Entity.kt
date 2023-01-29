package domain.model.base

import io.realm.kotlin.types.RealmObject

open class Entity: RealmObject {
    open val id: Int = 0
}