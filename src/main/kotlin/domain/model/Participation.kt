package domain.model

data class Participation(
    val id: Int,
    val studentId: Int,
    val projectId: Int,
    val priority: Int
)
