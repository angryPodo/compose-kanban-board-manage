package woowacourse.kanban.board.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.Tag

class TaskFormState {
    var title by mutableStateOf("")
    var isTitleDirty by mutableStateOf(false)
    var description by mutableStateOf("")
    var tagValue by mutableStateOf("")

    val isTitleError: Boolean
        get() = isTitleDirty && !KanbanTask.isTitleValid(title)

    val tags: List<Tag>
        get() {
            if (tagValue.isBlank()) return emptyList()
            return tagValue.split(",")
                .map { it.trim() }
                .filter { Tag.isValid(it) }
                .map { Tag(it) }
        }

    val rawTags: List<String>
        get() {
            if (tagValue.isBlank()) return emptyList()
            return tagValue.split(",").map { it.trim() }
        }

    val isTagCountError: Boolean
        get() = rawTags.size > 5

    val isTagFormatError: Boolean
        get() = tagValue.isNotBlank() && !rawTags.all { Tag.isValid(it) }

    val isCreateButtonEnabled: Boolean
        get() = KanbanTask.isTitleValid(title) && !isTagCountError && !isTagFormatError
}

@Composable
fun rememberTaskFormState(): TaskFormState = remember { TaskFormState() }
