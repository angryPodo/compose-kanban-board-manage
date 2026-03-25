package woowacourse.kanban.board.feature.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import woowacourse.kanban.board.feature.board.component.KanbanBoardContent
import woowacourse.kanban.board.feature.board.component.dialog.TaskDialog

@Composable
fun KanbanBoardScreen(
    onShowSnackbar: (String) -> Unit,
    modifier: Modifier = Modifier,
    boardState: KanbanBoardState = rememberKanbanBoardState(),
) {
    LaunchedEffect(boardState.snackbarMessage) {
        boardState.snackbarMessage?.let { message ->
            onShowSnackbar(message)
            boardState.clearSnackbar()
        }
    }

    KanbanBoardContent(
        modifier = modifier,
        kanbanBoard = boardState.kanbanBoard,
        onTaskCreateClick = boardState::showTaskDialog,
    )

    if (boardState.isTaskDialogVisible) {
        TaskDialog(
            onCreateClick = boardState::addTask,
            onDismissClick = boardState::hideTaskDialog,
        )
    }
}
