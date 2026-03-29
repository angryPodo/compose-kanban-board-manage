package woowacourse.kanban.board.feature.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.TaskStatus
import woowacourse.kanban.board.feature.board.component.dialog.model.TaskFormResult

@Stable
class KanbanBoardState(initialBoard: KanbanBoard = KanbanBoard()) {
    var kanbanBoard by mutableStateOf(initialBoard)
        private set
    var isTaskDialogVisible by mutableStateOf(false)
        private set
    var snackbarEvent: KanbanBoardEvent? by mutableStateOf(null)
        private set

    fun showTaskDialog() {
        isTaskDialogVisible = true
    }

    fun hideTaskDialog() {
        isTaskDialogVisible = false
    }

    fun clearSnackbar() {
        snackbarEvent = null
    }

    fun moveTask(task: KanbanTask, targetStatus: TaskStatus) {
        kanbanBoard = kanbanBoard.moveTask(task.id, targetStatus)
        snackbarEvent = KanbanBoardEvent.TaskMoved
    }

    fun addTask(result: TaskFormResult) {
        runCatching {
            val newTask = KanbanTask(
                title = result.title,
                description = result.description,
                tags = result.tags,
                status = result.status,
                crewName = result.assignee,
            )
            kanbanBoard = kanbanBoard.addTask(newTask)
            hideTaskDialog()
        }.onSuccess {
            snackbarEvent = KanbanBoardEvent.TaskAdded
        }.onFailure {
            snackbarEvent = KanbanBoardEvent.TaskAddFailed
        }
    }
}

@Composable
fun rememberKanbanBoardState(): KanbanBoardState = remember { KanbanBoardState() }
