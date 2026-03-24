package woowacourse.kanban.board.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KanbanBoardTest {

    @Test
    fun `전체_태스크_중_완료된_태스크의_비율을_계산한다`() {
        // given
        val tasks = listOf(
            createTask(status = TaskStatus.TODO),
            createTask(status = TaskStatus.DONE),
        )
        val board = KanbanBoard(tasks)

        // when
        val actual = board.completionRate

        // then
        assertThat(actual).isEqualTo(0.5f)
    }

    @Test
    fun `태스크가_없을_때_완료율은_0퍼센트이다`() {
        // given
        val tasks = emptyList<KanbanTask>()
        val board = KanbanBoard(tasks)

        // when
        val actual = board.completionRate

        // then
        assertThat(actual).isEqualTo(0.0f)
    }

    @Test
    fun `상태에_따라_분류된_태스크_목록을_정확히_반환한다`() {
        // given
        val todoTask = createTask(status = TaskStatus.TODO)
        val doneTask = createTask(status = TaskStatus.DONE)
        val board = KanbanBoard(listOf(todoTask, doneTask))

        // when
        val actual = board.getTasksByStatus(TaskStatus.TODO)

        // then
        assertThat(actual).containsExactly(todoTask)
        assertThat(actual).doesNotContain(doneTask)
    }

    @Test
    fun `보드에_새로운_태스크를_추가하면_새로운_보드_객체를_반환한다`() {
        // given
        val initialTask = createTask(title = "기존 태스크")
        val board = KanbanBoard(listOf(initialTask))
        val newTask = createTask(title = "새로운 태스크")

        // when
        val updatedBoard = board.addTask(newTask)

        // then
        assertThat(updatedBoard).isNotSameAs(board)
        assertThat(updatedBoard.tasks).containsExactly(initialTask, newTask)
    }

    @Test
    fun `상태별_태스크_개수를_정확히_반환한다`() {
        // given
        val todoTask1 = createTask(status = TaskStatus.TODO)
        val todoTask2 = createTask(status = TaskStatus.TODO)
        val inProgressTask = createTask(status = TaskStatus.IN_PROGRESS)
        val board = KanbanBoard(listOf(todoTask1, todoTask2, inProgressTask))

        // when & then
        assertThat(board.getCountByStatus(TaskStatus.TODO)).isEqualTo(2)
        assertThat(board.getCountByStatus(TaskStatus.IN_PROGRESS)).isEqualTo(1)
        assertThat(board.getCountByStatus(TaskStatus.DONE)).isEqualTo(0)
    }

    private fun createTask(title: String = "테스트 제목", status: TaskStatus = TaskStatus.TODO, crewName: String = "테스트 크루"): KanbanTask {
        return KanbanTask(
            title = title,
            status = status,
            crewName = crewName,
        )
    }
}
