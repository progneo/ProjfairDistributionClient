package domain.model

import com.google.gson.annotations.SerializedName

data class Project(
    val id: Int,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    val title: String,
    val places: Int,
    val goal: String?,
    val difficulty: Int,
    @SerializedName("date_start") val dateStart: String,
    @SerializedName("date_end") val dateEnd: String,
    val customer: String?,
    @SerializedName("additional_inf") val additionalInf: String?,
    @SerializedName("product_result") val productResult: String,
    @SerializedName("study_result") val studyResult: String,
    val supervisors: String,
)
