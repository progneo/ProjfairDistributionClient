package ui.preview.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.compose.ExposedTypedDropdownMenu
import common.compose.rememberExposedMenuStateHolder
import common.mapper.toShortInstitute
import domain.model.Department
import domain.model.Institute
import ui.filter.*

@Suppress("UNCHECKED_CAST")
@Composable
fun ProjectFilterDialog(
    visible: Boolean,
    instituteFilterConfiguration: InstituteFilterConfiguration,
    onApplyClicked: (InstituteFilterConfiguration) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var isInstituteSelected: Boolean by remember { mutableStateOf(false) }
    var isReset: Boolean by remember { mutableStateOf(false) }
    isInstituteSelected = instituteFilterConfiguration.filters[FilterType.INSTITUTE]!!.selectedValue != FilterSelectedType.All

    val instituteFilterMap = mutableStateMapOf<FilterType, FilterValue<FilterEntity>>()
    instituteFilterMap.putAll(instituteFilterConfiguration.filters)

    FilterDialog(
        visible = visible,
        filterContent = {
            Column {
                ProjectFilterDropdownItem<Institute>(
                    filterType = FilterType.INSTITUTE,
                    filterValue = instituteFilterMap[FilterType.INSTITUTE]!! as FilterValue<Institute>,
                    isEnabled = true,
                    isReset = isReset,
                ) { index, itemClicked ->
                    instituteFilterMap[FilterType.INSTITUTE]!!
                        .selectedValue =
                        if (index == 0) FilterSelectedType.All
                        else FilterSelectedType.Selected(
                            instituteFilterMap[FilterType.INSTITUTE]!!.values.find { filterEntity -> filterEntity.name == itemClicked }!!
                        )

                    isInstituteSelected = index != 0
                    isReset = false
                }
                Spacer(Modifier.size(32.dp))
                ProjectFilterDropdownItem<Department>(
                    filterType = FilterType.DEPARTMENT,
                    filterValue = instituteFilterMap[FilterType.DEPARTMENT]!! as FilterValue<Department>,
                    isEnabled = isInstituteSelected,
                    isReset = isReset,
                ) { index, itemClicked ->
                    instituteFilterMap[FilterType.DEPARTMENT]!!
                        .selectedValue =
                        if (index == 0) FilterSelectedType.All
                        else FilterSelectedType.Selected(
                            instituteFilterMap[FilterType.DEPARTMENT]!!.values.find { filterEntity -> filterEntity.name == itemClicked }!!
                        )

                    isReset = false
                }
            }
        },
        onApplyClicked = {
            if (!isInstituteSelected) {
                instituteFilterMap[FilterType.DEPARTMENT]!!.selectedValue = FilterSelectedType.All
            }
            onApplyClicked(instituteFilterConfiguration.copy(instituteFilterMap))
            onDismissRequest()
        },
        onResetFilters = {
            instituteFilterMap[FilterType.INSTITUTE]!!.selectedValue = FilterSelectedType.All
            instituteFilterMap[FilterType.DEPARTMENT]!!.selectedValue = FilterSelectedType.All
            isInstituteSelected = false
            isReset = true
        },
        onDismissRequest = {
            onDismissRequest()
        }
    )
}

@Composable
private fun <T : FilterEntity> ProjectFilterDropdownItem(
    filterType: FilterType,
    filterValue: FilterValue<T>,
    isEnabled: Boolean,
    isReset: Boolean,
    onClick: (Int, String) -> Unit,
) {
    val stateHolder = rememberExposedMenuStateHolder()
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = filterType.title,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(Modifier.size(16.dp))
        ExposedTypedDropdownMenu<T>(
            modifier = Modifier
                .size(width = 400.dp, height = Dp.Unspecified),
            title = filterValue.selectedValue.filterEntity.name,
            isTitleChangeable = true,
            stateHolder = stateHolder,
            items = filterValue.values,
            isEnabled = isEnabled,
            isReset = isReset,
            toShortName = String::toShortInstitute
        ) { index, itemClicked ->
            onClick(index, itemClicked)
        }
    }
}

class InstituteFilterConfiguration(
    val institutes: List<Institute>,
    val departments: List<Department>,
) : FilterConfiguration {
    override var filters: MutableMap<FilterType, FilterValue<FilterEntity>> = mutableMapOf()
        private set

    init {
        if (filters.isEmpty()) {
            filters = mutableMapOf(
                FilterType.INSTITUTE to FilterValue(
                    values = listOf(BaseAllFilterEntity()) + institutes,
                    selectedValue = FilterSelectedType.All
                ),
                FilterType.DEPARTMENT to FilterValue(
                    values = listOf(BaseAllFilterEntity()) + departments,
                    selectedValue = FilterSelectedType.All
                ),
            )
        }
    }

    override fun copy(copyFilters: MutableMap<FilterType, FilterValue<FilterEntity>>?): InstituteFilterConfiguration {
        val newFilters = copyFilters ?: mutableMapOf()

        this@InstituteFilterConfiguration.filters.forEach { (key, value) ->
            newFilters[key] = value.copy()
        }

        return InstituteFilterConfiguration(
            this.institutes,
            this.departments
        ).apply {
            filters = newFilters
        }
    }
}