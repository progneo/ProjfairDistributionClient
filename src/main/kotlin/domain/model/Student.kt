package domain.model

import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class Student(
    @PrimaryKey override var id: Int,
    var numz: Int,
    var name: String,
    var group: String,
    var shouldDistribute: Boolean,
    var specialtyId: Int
): Entity(), RealmObject {

    constructor(): this(0, 0, "", "", true, 0)

    override fun equals(other: Any?): Boolean {
        if (other === this) return true

        return other is Student &&
                other.id == this.id &&
                other.numz == this.numz &&
                other.name == this.name &&
                other.group == this.group &&
                other.shouldDistribute == this.shouldDistribute &&
                other.specialtyId == this.specialtyId
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
