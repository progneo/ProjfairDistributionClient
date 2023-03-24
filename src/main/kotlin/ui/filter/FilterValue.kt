package ui.filter

data class FilterValue<T: FilterEntity>(
    val values: List<T>,
    var selectedValue: FilterSelectedType,
)