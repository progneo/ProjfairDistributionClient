package domain.usecase.file

import common.file.ExportDataToJson
import domain.model.GeneratedDistribution
import java.io.File
import javax.inject.Inject

class RemoveGeneratedDistributionUseCase @Inject constructor() {

    operator fun invoke() {
        val path = "/generated_distributions/0.json"
        File(path).delete()
    }
}