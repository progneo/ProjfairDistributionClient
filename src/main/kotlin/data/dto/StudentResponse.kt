package data.dto

import com.google.gson.annotations.SerializedName

data class StudentResponse(
    val id: Int,
    val fio: String,
    val numz: String,
    val course: Int,
    @SerializedName("training_group") val trainingGroup: String,
    val specialty: SpecialtyResponse
)
