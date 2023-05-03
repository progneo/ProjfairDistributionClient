package domain.model

import com.google.gson.Gson
import ru.student.distribution.model.DistributionResults

data class GeneratedDistribution(
    val id: Int,
    val results: DistributionResults
) {

    fun toJson(): String {
        return Gson().toJson(this)
    }
}

fun String.fromJson(): GeneratedDistribution {
    return Gson().fromJson(this, GeneratedDistribution::class.java)
}
