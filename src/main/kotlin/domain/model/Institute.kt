package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import ui.filter.FilterEntity

class Institute(
    @PrimaryKey override var id: Int,
    override var name: String
): Entity(), RealmObject, FilterEntity {

    constructor() : this(0, "")
}