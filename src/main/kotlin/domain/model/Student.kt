package domain.model

data class Student(
    val id: Int,
    val name: String,
    val group: String,
    var shouldDistribute: Boolean,
    val specialtyId: Int
)
