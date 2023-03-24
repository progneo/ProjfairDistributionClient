package ui.filter

interface FilterEntity {
    val name: String
}

data class BaseAllFilterEntity(
    override val name: String = "Все",
): FilterEntity