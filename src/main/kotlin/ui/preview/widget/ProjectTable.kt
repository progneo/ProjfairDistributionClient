package ui.preview.widget

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import common.compose.rememberForeverLazyListState
import common.theme.BlueMainDark
import common.theme.BlueMainLight
import common.theme.GrayLight
import compose.icons.FontAwesomeIcons
import compose.icons.TablerIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.SortAlphaDown
import compose.icons.tablericons.ArrowsSort
import domain.model.Project
import kotlinx.coroutines.launch
import navigation.Bundle
import navigation.NavController
import navigation.ScreenRoute
import ui.common.BaseGodViewModel

private const val KEY = "PREVIEW_PROJECTS"

@Composable
fun ProjectTableItem(
    modifier: Modifier = Modifier,
    project: Project,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = project.name,
            modifier = Modifier
                .fillMaxWidth(0.6f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = project.freePlaces.toString(),
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .wrapContentWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = project.department?.institute?.name ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ProjectTableHead(
    modifier: Modifier = Modifier,
    instituteSelected: Boolean,
    onInstituteClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(GrayLight)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Название",
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .wrapContentWidth()
        )
        Text(
            text = "Свободные места",
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .wrapContentWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onInstituteClicked()
                }
                .wrapContentWidth()
        ) {
            Text(
                text = "Институт",
                modifier = Modifier
                    .wrapContentWidth()
            )
            Spacer(Modifier.size(8.dp))
            Icon(
                modifier = Modifier.size(20.dp, 20.dp),
                imageVector = if (instituteSelected) FontAwesomeIcons.Solid.SortAlphaDown else TablerIcons.ArrowsSort,
                contentDescription = null,
                tint = BlueMainDark
            )
        }
    }
    Divider(thickness = 2.dp)
}

@Composable
fun ProjectTable(
    modifier: Modifier = Modifier,
    projects: List<Project>,
    navController: NavController,
    baseGodViewModel: BaseGodViewModel,
    instituteSelected: Boolean,
    onInstituteClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, BlueMainLight),
                RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            )
    ) {
        ProjectTableHead(
            modifier = Modifier.fillMaxWidth(),
            instituteSelected = instituteSelected,
            onInstituteClicked = onInstituteClicked
        )

        val scrollState = rememberForeverLazyListState(KEY)
        val coroutineScope = rememberCoroutineScope()

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxWidth()
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            coroutineScope.launch {
                                scrollState.scrollBy(-delta)
                            }
                        }
                    ),

                ) {
                items(projects) { project ->
                    ProjectTableItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val bundle = Bundle().apply {
                                    put("project", project)
                                }
                                println(bundle)
                                navController.navigate(
                                    ScreenRoute.PROJECT_DETAILS,
                                    bundle,
                                    baseGodViewModel
                                )
                            },
                        project = project
                    )
                    Divider()
                }
            }

            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(
                    scrollState = scrollState
                ),
                style = ScrollbarStyle(
                    minimalHeight = LocalScrollbarStyle.current.minimalHeight,
                    thickness = 16.dp,
                    shape = LocalScrollbarStyle.current.shape,
                    hoverDurationMillis = LocalScrollbarStyle.current.hoverDurationMillis,
                    unhoverColor = LocalScrollbarStyle.current.unhoverColor,
                    hoverColor = LocalScrollbarStyle.current.hoverColor
                )
            )
        }
    }
}