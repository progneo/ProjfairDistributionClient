package domain.usecase.file

import common.file.ExportDataToJson
import domain.model.GeneratedDistribution
import java.io.File
import javax.inject.Inject

class SaveStudentsByProjectsFileUseCase @Inject constructor() {

    operator fun  invoke(generatedDistribution: GeneratedDistribution) {
        val path = "/generated_distributions/${generatedDistribution.id}.json"
        File(path).createNewFile()
        ExportDataToJson.exportGeneratedDistribution(
            filePath = path,
            generatedDistribution = generatedDistribution
        )
    }
}