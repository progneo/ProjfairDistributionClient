package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Institute(
    @PrimaryKey override var id: Int,
    var name: String
): Entity(), RealmObject {

    constructor() : this(0, "")
}