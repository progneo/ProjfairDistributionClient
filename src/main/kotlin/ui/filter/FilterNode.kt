package ui.filter

data class FilterNode(
    val prev: FilterType?,
    val type: FilterType,
    var selectedValue: FilterEntity?,
    val next: FilterType?
)
