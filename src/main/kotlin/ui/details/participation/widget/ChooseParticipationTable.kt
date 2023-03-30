package ui.details.participation.widget

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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import common.compose.rememberForeverLazyListState
import common.theme.BlueMainLight
import common.theme.GrayLight
import domain.model.Department
import domain.model.Institute
import domain.model.Project
import domain.model.Student
import kotlinx.coroutines.launch
import ui.filter.FilterEntity
import ui.filter.FilterNode
import ui.filter.FilterType
import ui.preview.viewmodel.PreviewViewModel

private const val KEY = "PROJECT_PARTICIPATION"

@Composable
fun ChooseParticipationTableItem(
    modifier: Modifier = Modifier,
    item: FilterEntity,
    onClicked: (FilterEntity) -> Unit
) {
    Row(
        modifier = modifier
            .clickable {
                onClicked(item)
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = item.name,
            modifier = Modifier
                .fillMaxWidth(0.6f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ChooseParticipationTableHead(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(GrayLight)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Текущий элемент",
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .wrapContentWidth()
        )
    }
    Divider(thickness = 2.dp)
}

@Composable
fun ChooseParticipationTable(
    modifier: Modifier = Modifier,
    filterNode: FilterNode,
    previewViewModel: PreviewViewModel,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, BlueMainLight),
                RoundedCornerShape(10.dp)
            )
    ) {
        ChooseParticipationTableHead(
            modifier = Modifier.fillMaxWidth()
        )

        val scrollState = rememberForeverLazyListState(KEY)
        val coroutineScope = rememberCoroutineScope()

        val filterTypeOrder = listOf<FilterType>(
            FilterType.INSTITUTE,
            FilterType.DEPARTMENT,
            FilterType.PROJECT,
            FilterType.STUDENT,
        )

        val filterStack = ArrayDeque<FilterNode>()
        filterStack.addLast(filterNode)

//        var currentFilterNode by remember { mutableStateOf(filterNode) }
        var currentItems by remember { mutableStateOf(previewViewModel.getValuesByType(filterStack.last().type)) }

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
            items(currentItems) { item ->
                ChooseParticipationTableItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    item = item,
                    onClicked = { clickedFilter ->
                        val currentFilterNode = filterStack.last()
                        if (currentFilterNode.next != null) {
                            val newFilterNode = FilterNode(
                                prev = currentFilterNode.type,
                                type = currentFilterNode.next,
                                selectedValue = clickedFilter,
                                next = if (filterStack.size+1 == filterTypeOrder.size) null
                                else {
                                    filterTypeOrder[filterStack.size+1]
                                }
                            )
                            filterStack.addLast(newFilterNode)
                            println(filterStack)
                            println(filterStack.last().type)
                            currentItems = previewViewModel.getValuesByType(
                                filterType = filterStack.last().type,
                                institute = clickedFilter as? Institute,
                                department = clickedFilter as? Department,
                                project = clickedFilter as? Project
                            )
                        } else {
                            println((clickedFilter as Student).name)
                        }
                    }
                )
                Divider()
            }
        }
    }
}
