package domain

import domain.model.Institute
import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Department(
    @PrimaryKey override var id: Int,
    var name: String,
    var institute: Institute? = null
): Entity(), RealmObject {

    constructor() : this(0, "", null)
}