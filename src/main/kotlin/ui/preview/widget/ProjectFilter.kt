package ui.preview.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.compose.ExposedDropdownMenu
import common.compose.ExposedTypedDropdownMenu
import common.compose.rememberExposedMenuStateHolder
import common.mapper.toShortInstitute
import common.theme.BlueMainLight
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
    val tempConfiguration = instituteFilterConfiguration.copy()

    var isInstituteSelected: Boolean by remember { mutableStateOf(false) }
    isInstituteSelected = tempConfiguration.filters[FilterType.INSTITUTE]!!.selectedValue != FilterSelectedType.All

    FilterDialog(
        visible = visible,
        filterContent = {
            Column {
                Text(text = "Фильтры", color = BlueMainLight, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.size(16.dp))

                ProjectFilterDropdownItem<Institute>(
                    filterType = FilterType.INSTITUTE,
                    filterValue = tempConfiguration.filters[FilterType.INSTITUTE]!! as FilterValue<Institute>,
                    isEnabled = true
                ) { index, itemClicked ->
                    println(itemClicked)
                    tempConfiguration
                        .filters[FilterType.INSTITUTE]!!
                        .selectedValue =
                        if (index == 0) FilterSelectedType.All
                        else FilterSelectedType.Selected(
                            tempConfiguration.filters[FilterType.INSTITUTE]!!.values.find { filterEntity -> filterEntity.name == itemClicked }!!
                        )

                    isInstituteSelected = index != 0
                }
                Spacer(Modifier.size(32.dp))
                ProjectFilterDropdownItem<Department>(
                    filterType = FilterType.DEPARTMENT,
                    filterValue = tempConfiguration.filters[FilterType.DEPARTMENT]!! as FilterValue<Department>,
                    isEnabled = isInstituteSelected
                ) { index, itemClicked ->
                    println("\"$itemClicked\" in ${tempConfiguration.filters[FilterType.INSTITUTE]!!.values.map { it.name }}")
                    tempConfiguration
                        .filters[FilterType.DEPARTMENT]!!
                        .selectedValue =
                        if (index == 0) FilterSelectedType.All
                        else FilterSelectedType.Selected(
                            tempConfiguration.filters[FilterType.DEPARTMENT]!!.values.find { filterEntity -> filterEntity.name == itemClicked }!!
                        )
                }

            }
        },
        onApplyClicked = {
            if (!isInstituteSelected) {
                tempConfiguration.filters[FilterType.DEPARTMENT]!!.selectedValue = FilterSelectedType.All
            }
            onApplyClicked(tempConfiguration)
            onDismissRequest()
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
    onClick: (Int, String) -> Unit
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

    override fun copy(): InstituteFilterConfiguration {
        val newFilters = mutableMapOf<FilterType, FilterValue<FilterEntity>>()

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