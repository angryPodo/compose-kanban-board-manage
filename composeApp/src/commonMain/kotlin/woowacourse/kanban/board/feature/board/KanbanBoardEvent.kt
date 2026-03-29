package woowacourse.kanban.board.feature.board

sealed class KanbanBoardEvent {
    data object TaskMoved : KanbanBoardEvent()
    data object TaskAdded : KanbanBoardEvent()
    data object TaskAddFailed : KanbanBoardEvent()
}
