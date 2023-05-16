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
import kotlinx.coroutines.launch
import navigation.Bundle
import navigation.NavController
import navigation.ScreenRoute
import ui.common.BaseGodViewModel
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
    var title by rememberSaveable {
        mutableStateOf(project.name)
    }

    var supervisors = rememberSaveable {
        mutableStateListOf(*project.supervisors.toTypedArray())
    }
    var goal by remember {
        mutableStateOf(project.goal)
    }
    var customer by remember {
        mutableStateOf(project.customer)
    }
    var description by remember {
        mutableStateOf(project.description)
    }
    var productResult by remember {
        mutableStateOf(project.productResult)
    }
    var studyResult by remember {
        mutableStateOf(project.studyResult)
    }
    var updatedDistributeSpecialties by remember {
        mutableStateOf(
            project.projectSpecialties.filter { ps ->
                ps.priority == null || ps.priority == 1
            }
        )
    }
    var updatedParticipationSpecialties by remember {
        mutableStateOf(
            project.projectSpecialties.filter { ps ->
                ps.priority == null || ps.priority == 2
            }
        )
    }

    var showSupervisorSearch by remember { mutableStateOf(false) }
    val supervisorSearchItems = viewModel.searchSupervisors.collectAsState()

    rememberCoroutineScope().launch {
        viewModel.searchSupervisorString.collect {
            viewModel.filterSupervisors()
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.verticalScroll(ScrollState(0))
    ) {
        val distributeSpecialtyStateHolder = rememberExposedMenuStateHolder()
        val participationSpecialtyStateHolder = rememberExposedMenuStateHolder()

        val distributeSpecialties = rememberSaveable {
            mutableStateListOf<ProjectSpecialty>(*project.projectSpecialties.filter { ps ->
                ps.priority == null || ps.priority == 1
            }.toTypedArray())
        }

        val participationSpecialties = rememberSaveable {
            mutableStateListOf<ProjectSpecialty>(*project.projectSpecialties.filter { ps ->
                ps.priority == null || ps.priority == 2
            }.toTypedArray())
        }

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
                updatedDistributeSpecialties = it
            })
        SpecialtyPicker(modifier = Modifier.width(200.dp),
            title = "Специальности для заявок",
            itemsState = participationSpecialties,
            stateHolder = participationSpecialtyStateHolder,
            dropdownItems = viewModel.specialties.value,
            priority = 2,
            onDataChange = {
                updatedParticipationSpecialties = it
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
                    projectSpecialties = realmListOf(*(updatedDistributeSpecialties + updatedParticipationSpecialties).toTypedArray())
                )

                viewModel.updateProject(projectToUpdate)
            }
            ShowParticipationButton {
                val bundle = Bundle()
                bundle.put("project", project)
                navController.navigate(ScreenRoute.PARTICIPATION_DETAILS, bundle, viewModel)
            }
            BaseButton(
                icon = Icons.Default.Refresh
            ) {
                viewModel.syncProject(project.id) {
                    val newProject = viewModel.getProjectById(project.id)!!
                    title = newProject.name
                    supervisors = mutableStateListOf(*newProject.supervisors.toTypedArray())
                    goal = newProject.goal
                    customer = newProject.customer
                    description = newProject.description
                    productResult = newProject.productResult
                    studyResult = newProject.studyResult
                    updatedDistributeSpecialties = newProject.projectSpecialties.filter { ps ->
                        ps.priority == null || ps.priority == 1
                    }
                    updatedParticipationSpecialties = newProject.projectSpecialties.filter { ps ->
                        ps.priority == null || ps.priority == 2
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