package com.example.testing_day1.data.source

import com.example.testing_day1.data.Result
import com.example.testing_day1.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual

import org.junit.Before
import org.junit.Test


class DefaultTasksRepositoryTest {

    private lateinit var fakeTasksRemoteDataSource: TasksDataSource
    private lateinit var fakeTasksLocalDataSource: TasksDataSource
    private lateinit var repository: DefaultTasksRepository

    private val localTasks = mutableListOf(
        Task("task1"),
        Task("task2"),
    )

    private val remoteTasks = mutableListOf(
        Task("task1"),
        Task("task2"),
    )

    @Before
    fun setup() {
        fakeTasksRemoteDataSource = FakeTasksRemoteDataSource(remoteTasks)
        fakeTasksLocalDataSource = FakeTasksLocalDataSource(localTasks)
        repository = DefaultTasksRepository(
            tasksRemoteDataSource = fakeTasksRemoteDataSource,
            tasksLocalDataSource = fakeTasksLocalDataSource
        )
    }


    @Test
    fun getTasks_forceUpdateFalse_returnsLocalDataFromLocal() = runTest() {
        val tasks = repository.getTasks(false) as Result.Success
        assertThat(tasks.data, IsEqual(localTasks))
    }

    @Test
    fun getTasks_forceUpdateFalse_returnsRemoteDataFromLocal() = runTest() {
        val tasks = repository.getTasks(true) as Result.Success
        assertThat(tasks.data, IsEqual(remoteTasks))
    }
}