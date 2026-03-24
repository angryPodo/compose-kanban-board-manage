package woowacourse.kanban.board.component.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.component.dialog.model.TaskFormResult
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanTask

class KanbanBoardState(initialBoard: KanbanBoard = KanbanBoard()) {
    var kanbanBoard by mutableStateOf(initialBoard)
        private set
    var isTaskDialogVisible by mutableStateOf(false)
        private set

    fun showTaskDialog() {
        isTaskDialogVisible = true
    }

    fun hideTaskDialog() {
        isTaskDialogVisible = false
    }

    fun addTask(result: TaskFormResult): Result<Unit> = runCatching {
        val newTask = KanbanTask(
            title = result.title,
            description = result.description,
            tags = result.tags,
            status = result.status,
            crewName = result.assignee,
        )
        kanbanBoard = kanbanBoard.addTask(newTask)
        hideTaskDialog()
    }
}

@Composable
fun rememberKanbanBoardState(): KanbanBoardState = remember { KanbanBoardState() }
