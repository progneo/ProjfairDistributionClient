package domain.model

import com.google.gson.annotations.SerializedName
import domain.model.base.Entity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class Project(
    @PrimaryKey override var id: Int,
    var title: String,
    var places: Int,
    var freePlaces: Int = places,
    var goal: String?,
    var difficulty: Int,
    var description: String?,
    @SerializedName("date_start") var dateStart: String,
    @SerializedName("date_end") var dateEnd: String,
    var customer: String?,
    @SerializedName("product_result") var productResult: String,
    @SerializedName("study_result") var studyResult: String,
    @SerializedName("supervisorsNames") var supervisors: String,
) : Entity(), RealmObject {

    constructor() : this(
        0,
        "",
        0,
        0,
        null,
        0,
        null,
        "",
        "",
        null,
        "",
        "",
        ""
    )
}
