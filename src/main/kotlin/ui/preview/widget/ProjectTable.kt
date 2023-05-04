package ui.preview.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import common.compose.rememberForeverLazyListState
import common.theme.BlueMainLight
import common.theme.GrayLight
import domain.model.Project
import kotlinx.coroutines.launch
import navigation.Bundle
import navigation.NavController
import navigation.ScreenRoute

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
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(GrayLight)
            .padding(horizontal = 16.dp, vertical = 8.dp)
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
        Text(
            text = "Институт",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        )
    }
    Divider(thickness = 2.dp)
}

@Composable
fun ProjectTable(
    modifier: Modifier = Modifier,
    projects: List<Project>,
    navController: NavController,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, BlueMainLight),
                RoundedCornerShape(10.dp)
            )
    ) {
        ProjectTableHead(
            modifier = Modifier.fillMaxWidth()
        )

        val scrollState = rememberForeverLazyListState(KEY)
        val coroutineScope = rememberCoroutineScope()

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
                            navController.navigate(ScreenRoute.PROJECT_DETAILS, bundle)
                        },
                    project = project
                )
                Divider()
            }
        }
    }
}