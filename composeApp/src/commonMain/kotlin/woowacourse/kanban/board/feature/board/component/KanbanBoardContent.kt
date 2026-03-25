package woowacourse.kanban.board.feature.board.component

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.TaskStatus

@Composable
fun KanbanBoardContent(kanbanBoard: KanbanBoard, onTaskCreateClick: () -> Unit, modifier: Modifier = Modifier) {
    with(kanbanBoard) {
        Column(
            modifier = modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            KanbanBoardHeader(
                board = kanbanBoard,
                onClick = onTaskCreateClick,
                modifier = Modifier,
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(24.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TaskStatus.entries.forEach { status ->
                    KanbanColumn(
                        status = status,
                        tasks = getTasksByStatus(status),
                    )
                }
            }
        }
    }
}

@Preview(device = Devices.TABLET)
@Composable
private fun KanbanBoardScreenPreview() {
    KanbanBoardContent(
        kanbanBoard = KanbanBoard(emptyList()),
        onTaskCreateClick = {},
    )
}
