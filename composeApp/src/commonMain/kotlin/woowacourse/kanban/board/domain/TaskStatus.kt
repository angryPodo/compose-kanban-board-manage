package woowacourse.kanban.board.domain

enum class TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE,
    ;

    fun isTransitionableTo(target: TaskStatus): Boolean = this != target
}
