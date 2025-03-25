package com.example.testing_day1.data.source.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.testing_day1.data.Result
import com.example.testing_day1.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class TasksLocalDataSourceTest {
    private lateinit var database: ToDoDatabase
    private lateinit var tasksLocalDataSource: TasksLocalDataSource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room.inMemoryDatabaseBuilder(context, ToDoDatabase::class.java).allowMainThreadQueries()
                .build()

        tasksLocalDataSource = TasksLocalDataSource(database.taskDao(), Dispatchers.Main)
    }

    @Test
    fun saveTask_retrievesTask() = runTest {
        val task = Task("title", "description")
        tasksLocalDataSource.saveTask(task)

        val result = tasksLocalDataSource.getTask(task.id)

        result as Result.Success
        assertThat(result.data.id, `is`(task.id))
        assertThat(result.data.title, `is`(task.title))
        assertThat(result.data.description, `is`(task.description))
        assertThat(result.data.isCompleted, `is`(task.isCompleted))
    }

    @Test
    fun completeTask_retrievedTaskIsComplete() = runTest {
        val task = Task("title", "description")
        tasksLocalDataSource.saveTask(task)

        tasksLocalDataSource.completeTask(task.id)
        val result = tasksLocalDataSource.getTask(task.id)

        result as Result.Success
        assertThat(result.data.isCompleted, `is`(true))
    }
}