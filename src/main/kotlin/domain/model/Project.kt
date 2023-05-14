package domain.model

import com.google.gson.annotations.SerializedName
import domain.model.base.Entity
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import ui.filter.FilterEntity

open class Project(
    @PrimaryKey override var id: Int,
    @SerializedName("title") override var name: String,
    var places: Int,
    var freePlaces: Int = places, //TODO: update with participations size
    var goal: String,
    var difficulty: Int,
    var description: String,
    @SerializedName("date_start") var dateStart: String,
    @SerializedName("date_end") var dateEnd: String,
    var customer: String,
    @SerializedName("product_result") var productResult: String,
    @SerializedName("study_result") var studyResult: String,
    var department: Department? = null,
    var supervisors: RealmList<Supervisor> = realmListOf(),
    @SerializedName("project_specialities") var projectSpecialties: RealmList<ProjectSpecialty> = realmListOf()
) : LoggingEntity(), RealmObject, FilterEntity {

    constructor() : this(
        0,
        "",
        0,
        0,
        "",
        0,
        "",
        "",
        "",
        "",
        "",
        "",
        null,
    )

    override fun members(): List<Any?> {
        return listOf(
            id,
            name,
            places,
            freePlaces,
            goal,
            difficulty,
            description,
            dateStart,
            dateEnd,
            customer,
            productResult,
            studyResult,
            supervisors,
            department,
            projectSpecialties
        )
    }

    override fun toLog(): String {
        return "id: $id, name: $name"
    }

    override fun toString(): String {
        return "{" +
                "id=$id," +
                "name=$name," +
                "department=$department," +
                "supervisors=$supervisors," +
                "projectSpecialties=$projectSpecialties" +
                "}"
    }
}

data class CleanProject(
    var id: Int,
    var name: String,
    var places: Int,
    var freePlaces: Int = places,
    var goal: String,
    var difficulty: Int,
    var description: String,
    var dateStart: String,
    var dateEnd: String,
    var customer: String,
    var productResult: String,
    var studyResult: String,
    var department: Department,
    var supervisors: List<Supervisor>,
    var projectSpecialties: List<CleanProjectSpecialty>
)
