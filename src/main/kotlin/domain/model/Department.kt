package domain.model

import domain.model.Institute
import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import ui.filter.FilterEntity

class Department(
    @PrimaryKey override var id: Int,
    override var name: String,
    var institute: Institute? = null
): Entity(), RealmObject, FilterEntity {

    constructor() : this(0, "", null)
}