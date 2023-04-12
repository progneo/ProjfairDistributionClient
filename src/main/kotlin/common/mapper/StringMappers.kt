package common.mapper

fun String.toShortName(): String {
    val name = this.split(" ")
    var shortName = "${name[0]} "
    (1..name.lastIndex).forEach {
        shortName += "${name[it][0].uppercase()}."
    }

    return shortName
}

fun String.toShortInstitute(): String {
    val name = this
        .replace("\"", "")
        .split(" ")

    if (name.size == 1) return this

    println("\"$this\"")

    var shortName = ""
    (0..name.lastIndex).forEach {
        if (name[it].isEmpty()) return@forEach
        shortName += name[it][0].uppercase()
    }

    return shortName
}