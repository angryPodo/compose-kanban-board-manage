package woowacourse.kanban.board.feature.board

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import woowacourse.kanban.board.feature.board.component.KanbanBoardContent
import woowacourse.kanban.board.feature.board.component.KanbanBoardSidebar
import woowacourse.kanban.board.feature.board.component.dialog.TaskDialog

@Composable
fun KanbanBoardScreen(
    onShowSnackbar: (String) -> Unit,
    modifier: Modifier = Modifier,
    boardState: KanbanBoardState = rememberKanbanBoardState(),
) {
    LaunchedEffect(boardState.snackbarEvent) {
        val message = when (boardState.snackbarEvent) {
            KanbanBoardEvent.TaskMoved -> "태스크가 이동되었습니다."
            KanbanBoardEvent.TaskAdded -> "새로운 태스크가 추가되었습니다."
            KanbanBoardEvent.TaskAddFailed -> "태스크 추가에 실패했습니다."
            null -> null
        }
        message?.let {
            onShowSnackbar(it)
            boardState.clearSnackbar()
        }
    }

    Row(modifier = modifier.fillMaxSize()) {
        KanbanBoardSidebar()
        KanbanBoardContent(
            modifier = Modifier.weight(1f),
            kanbanBoard = boardState.kanbanBoard,
            onTaskCreateClick = boardState::showTaskDialog,
            onMoveTask = boardState::moveTask,
        )
    }

    if (boardState.isTaskDialogVisible) {
        TaskDialog(
            onCreateClick = boardState::addTask,
            onDismissClick = boardState::hideTaskDialog,
        )
    }
}
