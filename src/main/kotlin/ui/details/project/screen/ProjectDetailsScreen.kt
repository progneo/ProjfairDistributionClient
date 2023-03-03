package ui.details.project.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.Project
import navigation.NavController
import ui.details.project.widget.*

@Composable
fun ProjectDetailsScreen(
    navController: NavController,
    project: Project,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .verticalScroll(ScrollState(0))
    ) {
        val stateHolder = rememberExposedMenuStateHolder()
        val supervisors = remember {
            mutableStateListOf("Аршинский Вадим Леонидович", "Серышева Ирина Анатольевна", "Лукаш Олег")
        }
        val dropdownItems =
            mutableListOf<String>("Аршинский Вадим Леонидович", "Серышева Ирина Анатольевна", "Лукаш Олег")

        Row(modifier = Modifier.padding(16.dp)) {
            BackButton(navController = navController)
            Spacer(modifier = Modifier.size(16.dp))
            TitleField(title = project.title)
        }
        ExposedDropdownMenuWithChips(stateHolder, supervisors, dropdownItems)
        EditableDescriptionField(title = "Цель проекта", content = project.goal ?: "")
        BorderedRadioButtonGroupColumn(
            titles = listOf(
                Title("Легко"),
                Title("Сложно"),
                Title("Трудно")
            ),
            selected = project.difficulty - 1,
            title = "Сложность"
        )
        EditableDescriptionField(title = "Заказчик", content = project.customer ?: "")
        EditableDescriptionField(title = "Описание", content = project.description ?: "")
        EditableDescriptionField(title = "Ожидаемый продуктовый результат", content = project.productResult)
        EditableDescriptionField(title = "Ожидаемый учебный результат", content = project.studyResult)
        Spacer(modifier = Modifier.size(16.dp))
    }
}