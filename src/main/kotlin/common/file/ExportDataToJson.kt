package common.file

import com.google.gson.Gson
import domain.model.GeneratedDistribution
import java.io.FileWriter
import java.io.PrintWriter

object ExportDataToJson {

    fun exportGeneratedDistribution(filePath: String, generatedDistribution: GeneratedDistribution) {
        try {
            PrintWriter(FileWriter(filePath)).use {
                val gson = Gson()
                val jsonString = gson.toJson(generatedDistribution.toJson())
                it.write(jsonString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}