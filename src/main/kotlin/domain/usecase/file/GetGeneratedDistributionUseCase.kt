package domain.usecase.file

import domain.model.GeneratedDistribution
import domain.model.fromJson
import java.io.File
import javax.inject.Inject

//class GetGeneratedDistributionUseCase @Inject constructor() {
//
//    operator fun invoke(): GeneratedDistribution {
//        val path = "/generated_distributions/0.json"
//        val json = File(path).bufferedReader().use { it.readText() }
//
//        return json.fromJson()
//    }
//}