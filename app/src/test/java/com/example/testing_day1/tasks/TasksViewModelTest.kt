package com.example.testing_day1.tasks

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testing_day1.data.source.DefaultTasksRepository
import io.mockk.mockk
import net.bytebuddy.implementation.FixedValue.nullValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {


    private lateinit var viewModel: TasksViewModel
    private lateinit var repo: DefaultTasksRepository

    @Before
    fun setUp(){
        repo = mockk(relaxed = true)
        viewModel = TasksViewModel(repo)
    }

    @Test
    fun addNewTask() {
        viewModel.addNewTask()
        val result = viewModel.newTaskEvent.getOrAwaitValue { }
        assertThat(result, not(nullValue()))
    }

    @Test
    fun setFiltering() {
        viewModel.setFiltering(TasksFilterType.ALL_TASKS)
        val isTasksAddVisible = viewModel.tasksAddViewVisible.getOrAwaitValue { }
        assertThat(isTasksAddVisible, `is`(true))
    }
}