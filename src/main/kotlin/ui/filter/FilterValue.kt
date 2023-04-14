package ui.filter

data class FilterValue<T: FilterEntity>(
    var values: List<T>,
    var selectedValue: FilterSelectedType,
)