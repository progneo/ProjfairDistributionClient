package ui.filter

interface FilterConfiguration{
    val filters: MutableMap<FilterType, FilterValue<FilterEntity>>
    fun copy(copyFilters: MutableMap<FilterType, FilterValue<FilterEntity>>? = null): FilterConfiguration
}