package woowacourse.kanban.board.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import woowacourse.kanban.board.component.dialog.component.TaskDialogContent
import woowacourse.kanban.board.component.dialog.model.TaskFormResult
import woowacourse.kanban.board.domain.TaskStatus

@Composable
fun TaskDialog(
    onCreateClick: (result: TaskFormResult) -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
    assignees: List<String> = listOf("다이노", "페임스"),
) {
    val formState = rememberTaskFormState()
    val statuses = TaskStatus.entries

    var selectedStatusIndex by remember { mutableIntStateOf(0) }
    var selectedAssigneeIndex by remember { mutableIntStateOf(0) }

    Dialog(
        onDismissRequest = onDismissClick,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
    ) {
        TaskDialogContent(
            modifier = modifier,
            titleValue = formState.title,
            isTitleError = formState.isTitleError,
            onTitleChanged = {
                formState.title = it
                formState.isTitleDirty = true
            },
            descriptionValue = formState.description,
            onDescriptionChanged = { formState.description = it },
            tagValue = formState.tagValue,
            isTagCountError = formState.isTagCountError,
            isTagFormatError = formState.isTagFormatError,
            onTagChanged = { formState.tagValue = it },
            statuses = statuses,
            selectedStatusIndex = selectedStatusIndex,
            onStatusChanged = { selectedStatusIndex = it },
            assignees = assignees,
            selectedAssigneeIndex = selectedAssigneeIndex,
            onAssigneeChanged = { selectedAssigneeIndex = it },
            enabled = formState.isCreateButtonEnabled,
            onDismissClick = onDismissClick,
            onCreateClick = {
                onCreateClick(
                    TaskFormResult(
                        title = formState.title,
                        description = formState.description.takeIf { it.isNotBlank() },
                        tags = formState.tags,
                        status = statuses[selectedStatusIndex],
                        assignee = assignees[selectedAssigneeIndex],
                    ),
                )
            },
        )
    }
}
