package domain.model

import data.dto.InstituteResponse
import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class Specialty(
    @PrimaryKey override var id: Int,
    var name: String,
    var instituteResponse: InstituteResponse? = null
): Entity(), RealmObject {

    constructor() : this(0, "")
}
