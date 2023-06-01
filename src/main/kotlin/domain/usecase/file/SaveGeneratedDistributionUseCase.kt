package domain.usecase.file

import common.file.ExportDataToJson
import domain.model.GeneratedDistribution
import java.io.File
import javax.inject.Inject

//class SaveGeneratedDistributionUseCase @Inject constructor() {
//
//    operator fun invoke(generatedDistribution: GeneratedDistribution) {
//        val path = "${System.getProperty("user.dir")}/generated_distributions/${generatedDistribution.id}.json"
//        File("${System.getProperty("user.dir")}/generated_distributions/").mkdir()
//        File(path).createNewFile()
//        ExportDataToJson.exportGeneratedDistribution(
//            filePath = path,
//            generatedDistribution = generatedDistribution
//        )
//    }
//}