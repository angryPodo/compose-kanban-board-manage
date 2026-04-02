package woowacourse.kanban.board.domain

import java.util.UUID

data class KanbanBoard(val tasks: List<KanbanTask> = emptyList()) {
    fun getTasksByStatus(status: TaskStatus): List<KanbanTask> = tasks.filter { it.status == status }

    fun getCountByStatus(status: TaskStatus): Int = tasks.count { it.status == status }

    fun addTask(task: KanbanTask): KanbanBoard = copy(tasks = tasks + task)

    fun updateTask(task: KanbanTask): KanbanBoard {
        val updatedTasks = tasks.map { if (it.id == task.id) task else it }
        return copy(tasks = updatedTasks)
    }

    fun deleteTask(taskId: UUID): KanbanBoard = copy(tasks = tasks.filter { it.id != taskId || !it.status.isDeletable })

    fun moveTask(taskId: UUID, targetStatus: TaskStatus): KanbanBoard {
        val updatedTasks = tasks.map { task ->
            if (task.id != taskId) return@map task
            if (!task.status.isTransitionableTo(targetStatus)) return@map task
            if (targetStatus.isAssigneeRequired && task.crewName == null) return@map task
            task.copy(status = targetStatus)
        }
        return copy(tasks = updatedTasks)
    }

    val completionRate: Float
        get() {
            if (tasks.isEmpty()) return 0.0f
            val completeCount = getCountByStatus(TaskStatus.DONE)
            return completeCount.toFloat() / tasks.size
        }
}
