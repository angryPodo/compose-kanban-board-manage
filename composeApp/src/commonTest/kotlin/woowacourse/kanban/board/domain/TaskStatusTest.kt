package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class TaskStatusTest {

    @Test
    fun `다른 상태로의 전이는 유효하다`() {
        assertThat(TaskStatus.TODO.isTransitionableTo(TaskStatus.IN_PROGRESS)).isTrue()
        assertThat(TaskStatus.TODO.isTransitionableTo(TaskStatus.DONE)).isTrue()
        assertThat(TaskStatus.DONE.isTransitionableTo(TaskStatus.TODO)).isTrue()
    }

    @Test
    fun `동일한 상태로의 전이는 유효하지 않다`() {
        assertThat(TaskStatus.TODO.isTransitionableTo(TaskStatus.TODO)).isFalse()
        assertThat(TaskStatus.IN_PROGRESS.isTransitionableTo(TaskStatus.IN_PROGRESS)).isFalse()
        assertThat(TaskStatus.DONE.isTransitionableTo(TaskStatus.DONE)).isFalse()
    }
}
