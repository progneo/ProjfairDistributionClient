package common.date

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDateTime(): String {
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    return sdf.format(Date())
}

fun String.toDate(): Date {
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    return DateFormat.getDateInstance().parse(this)
}