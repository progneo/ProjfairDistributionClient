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
import common.compose.rememberExposedMenuStateHolder
import common.mapper.toShortInstitute
import common.theme.BlueMainLight
import domain.Department
import domain.model.Institute
import ui.filter.*

@Composable
fun ProjectFilterDialog(
    visible: Boolean,
    instituteFilterConfiguration: InstituteFilterConfiguration,
    onApplyClicked: (InstituteFilterConfiguration) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val tempConfiguration = instituteFilterConfiguration.copy()

    println("${tempConfiguration.filters[FilterType.INSTITUTE]!!.selectedValue} != ${FilterSelectedType.All} == ${tempConfiguration.filters[FilterType.INSTITUTE]!!.selectedValue != FilterSelectedType.All}")
    var isInstituteSelected: Boolean by remember { mutableStateOf(false) }
    isInstituteSelected = tempConfiguration.filters[FilterType.INSTITUTE]!!.selectedValue != FilterSelectedType.All

    FilterDialog(
        visible = visible,
        filterContent = {
            Column {
                Text(text = "Фильтры", color = BlueMainLight, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.size(16.dp))

                    ProjectFilterDropdownItem(
                        filterType = FilterType.INSTITUTE,
                        filterValue = tempConfiguration.filters[FilterType.INSTITUTE]!!,
                        isEnabled = true
                    ) { index, itemClicked ->
                        tempConfiguration
                            .filters[FilterType.INSTITUTE]!!
                            .selectedValue =
                            if (index == 0) FilterSelectedType.All
                            else FilterSelectedType.Selected(itemClicked)

                        println("here")
                        isInstituteSelected = index != 0
                    }
                    Spacer(Modifier.size(32.dp))
                    ProjectFilterDropdownItem(
                        filterType = FilterType.DEPARTMENT,
                        filterValue = tempConfiguration.filters[FilterType.DEPARTMENT]!!,
                        isEnabled = isInstituteSelected
                    ) { index, itemClicked ->
                        tempConfiguration
                            .filters[FilterType.DEPARTMENT]!!
                            .selectedValue =
                            if (index == 0) FilterSelectedType.All
                            else FilterSelectedType.Selected(itemClicked)
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
private fun ProjectFilterDropdownItem(
    filterType: FilterType,
    filterValue: FilterValue,
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
        ExposedDropdownMenu(
            modifier = Modifier
                .size(width = 400.dp, height = Dp.Unspecified),
            title = filterValue.selectedValue.name,
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
    override var filters: MutableMap<FilterType, FilterValue> = mutableMapOf()
        private set

    init {
        if (filters.isEmpty()) {
            filters = mutableMapOf(
                FilterType.INSTITUTE to FilterValue(
                    values = listOf(FILTER_ALL) + institutes.map { it.name },
                    selectedValue = FilterSelectedType.All
                ),
                FilterType.DEPARTMENT to FilterValue(
                    values = listOf(FILTER_ALL) + departments.map { it.name },
                    selectedValue = FilterSelectedType.All
                ),
            )
        }
    }

    override fun copy(): InstituteFilterConfiguration {
        val newFilters = mutableMapOf<FilterType, FilterValue>()

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