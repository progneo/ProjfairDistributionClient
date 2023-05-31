package ui.details.project.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.compose.*
import common.mapper.toShortName
import common.theme.BlueMainLight
import domain.model.Project
import domain.model.ProjectSpecialty
import domain.model.Supervisor
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import navigation.Bundle
import navigation.NavController
import navigation.ScreenRoute
import navigation.SharedScreen
import ui.common.BaseGodViewModel
import ui.common.BaseGodViewModelType
import ui.details.project.widget.*
import ui.preview.viewmodel.PreviewViewModel
import ui.preview.widget.SupervisorSearchDialog

@Composable
fun ProjectDetailsScreen(
    navController: NavController,
    viewModel: BaseGodViewModel,
    project: Project,
) {
    //new project parameters
    var title = project.name
    val supervisors = rememberSaveable {
        mutableStateListOf(*project.supervisors.toTypedArray())
    }
    var goal = project.goal
    var customer = project.customer
    var description = project.description
    var productResult = project.productResult
    var studyResult = project.studyResult
    var distributeSpecialties =
        mutableStateListOf<ProjectSpecialty>(*project.projectSpecialties.filter { ps ->
            ps.priority == null || ps.priority == 1
        }.toTypedArray())

    var participationSpecialties =
        mutableStateListOf<ProjectSpecialty>(*project.projectSpecialties.filter { ps ->
            ps.priority == null || ps.priority == 2
        }.toTypedArray())


    var showSupervisorSearch by remember { mutableStateOf(false) }
    val supervisorSearchItems = viewModel.searchSupervisors.collectAsState()

    rememberCoroutineScope().launch {
        viewModel.searchSupervisorString.collect {
            viewModel.filterSupervisors()
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.verticalScroll(ScrollState(0))
    ) {
        val distributeSpecialtyStateHolder = rememberExposedMenuStateHolder()
        val participationSpecialtyStateHolder = rememberExposedMenuStateHolder()

        Row(modifier = Modifier.padding(16.dp)) {
            BackButton(navController = navController)
            Spacer(modifier = Modifier.size(16.dp))
            TitleField(title = title) {
                title = it
            }
        }
        BorderedTitledComposable(
            title = "Преподаватели"
        ) {
            ChipsTypedVerticalGrid(
                modifier = Modifier.padding(8.dp),
                itemsState = supervisors,
                toShortName = String::toShortName,
                onItemRemoved = { supervisor ->
                    supervisors.remove(supervisor)
                }
            )
        }
        Button(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    bottom = 12.dp,
                ),
            onClick = {
                showSupervisorSearch = true
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = BlueMainLight,
                contentColor = Color.White
            )
        ) {
            Text("Выбрать преподавателя")
        }
        EditableDescriptionField(title = "Цель проекта", content = goal ?: "") {
            goal = it
        }
        BorderedRadioButtonGroupColumn(
            titles = listOf(
                Title("Легко"), Title("Средне"), Title("Сложно")
            ), selected = project.difficulty - 1, title = "Сложность"
        )
        EditableDescriptionField(title = "Заказчик", content = customer ?: "") {
            customer = it
        }
        EditableDescriptionField(title = "Описание", content = description ?: "") {
            description = it
        }
        EditableDescriptionField(title = "Ожидаемый продуктовый результат", content = productResult) {
            productResult = it
        }
        EditableDescriptionField(title = "Ожидаемый учебный результат", content = studyResult) {
            studyResult = it
        }
        SpecialtyPicker(modifier = Modifier.width(200.dp),
            title = "Специальности для молчунов",
            itemsState = distributeSpecialties,
            stateHolder = distributeSpecialtyStateHolder,
            dropdownItems = viewModel.specialties.value,
            priority = 1,
            onDataChange = {
                distributeSpecialties = mutableStateListOf(*it.toTypedArray())
            })
        SpecialtyPicker(modifier = Modifier.width(200.dp),
            title = "Специальности для заявок",
            itemsState = participationSpecialties,
            stateHolder = participationSpecialtyStateHolder,
            dropdownItems = viewModel.specialties.value,
            priority = 2,
            onDataChange = {
                participationSpecialties = mutableStateListOf(*it.toTypedArray())
            })
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            SaveButton {
                val projectToUpdate = Project(
                    id = project.id,
                    name = title,
                    places = project.places, //TODO: maybe update places somehow
                    freePlaces = project.freePlaces,
                    goal = goal,
                    difficulty = project.difficulty,
                    description = description,
                    dateStart = project.dateStart,
                    dateEnd = project.dateEnd,
                    customer = customer,
                    productResult = productResult,
                    studyResult = studyResult,
                    department = project.department,
                    supervisors = realmListOf(*supervisors.toTypedArray()),
                    projectSpecialties = realmListOf(*(distributeSpecialties + participationSpecialties).toTypedArray())
                )

                viewModel.updateProject(projectToUpdate)
            }
            ShowParticipationButton {
                val bundle = Bundle()
                bundle.put("project", project)
                val screenRoute = when (viewModel.getType()) {
                    BaseGodViewModelType.PREVIEW -> {
                        ScreenRoute.PARTICIPATION_DETAILS_PREVIEW
                    }
                    BaseGodViewModelType.REVIEW  -> {
                        ScreenRoute.PARTICIPATION_DETAILS_REVIEW
                    }
                    BaseGodViewModelType.NONE -> {
                        ScreenRoute.PARTICIPATION_DETAILS_PREVIEW
                    }
                }
                navController.navigate(screenRoute, bundle, viewModel)
            }
            BaseButton(
                icon = Icons.Default.Refresh
            ) {
                viewModel.syncProject(project.id) {
                    runBlocking {
                        navController.navigate(route = ScreenRoute.LOADING, addToBackStack = false)
                        delay(200)
                        val bundle = Bundle()
                        bundle.put("project", it)
                        navController.navigate(ScreenRoute.PROJECT_DETAILS, bundle, viewModel, false)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
    }

    SupervisorSearchDialog(
        visible = showSupervisorSearch,
        supervisors = supervisorSearchItems.value,
        searchString = viewModel.lastSearchSupervisorString.collectAsState().value,
        onDataChanges = { searchString ->
            viewModel.lastSearchSupervisorString.value = searchString
        },
        onSupervisorClicked = { supervisor ->
            supervisors.add(supervisor)
        },
        onDismissRequest = {
            viewModel.lastSearchSupervisorString.value = ""
            showSupervisorSearch = false
        }
    )
}