package domain.model

data class GeneratedDistribution(
    val id: Int,
    val projects: List<Project>,
    val participations: List<Participation>
)
