package common.student

import common.date.getCurrentDateTime
import data.dto.StudentResponse
import domain.model.Student
import java.text.DateFormat
import java.util.Calendar

fun StudentResponse.canParticipate(): Boolean {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR) - 2000
    val currentMonth = calendar.get(Calendar.MONTH)
    val studentYear = this.trainingGroup.takeLast(4).take(2).toInt()

    return (currentYear - studentYear >= 2)
}