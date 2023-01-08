package domain.model

data class Student(
    val id: Int,
    val name: String,
    val group: String,
    val shouldDistribute: Boolean,
    val specialityId: Int
)
