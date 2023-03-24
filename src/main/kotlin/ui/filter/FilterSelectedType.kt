package ui.filter

sealed class FilterSelectedType(val filterEntity: FilterEntity) {
    object All : FilterSelectedType(BaseAllFilterEntity())
    data class Selected(val value: FilterEntity) : FilterSelectedType(value)
}