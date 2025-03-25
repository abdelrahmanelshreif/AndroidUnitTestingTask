package com.example.testing_day1.data.source.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.testing_day1.data.Task
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {

    private lateinit var database: ToDoDatabase
    private lateinit var tasksDao: TasksDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ToDoDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        tasksDao = database.taskDao()
    }

    @Test
    fun saveTask_retrievesTask() = runTest {
        val tempTask = Task("TASK 1", "TASK 1 DESC")
        tasksDao.insertTask(tempTask)
        val retrievedTask = tasksDao.getTaskById(tempTask.id)

        assertThat(retrievedTask?.id, `is`(tempTask.id))
        assertThat(retrievedTask as Task, notNullValue())
        assertThat(retrievedTask.title, `is`(tempTask.title))
        assertThat(retrievedTask.description, `is`(tempTask.description))
        assertThat(retrievedTask.isCompleted, `is`(tempTask.isCompleted))
    }

    @Test
    fun updateTaskAndGetById() = runTest {
        val task = Task("initial title", "initial description")
        tasksDao.insertTask(task)

        val modifiedTask = Task("modified title", "modified description", true, task.id)
        tasksDao.updateTask(modifiedTask)

        val retrievedTask = tasksDao.getTaskById(task.id)
        assertThat(retrievedTask?.title, `is`("modified title"))
        assertThat(retrievedTask?.description, `is`("modified description"))
        assertThat(retrievedTask?.isCompleted, `is`(true))
    }

    @Test
    fun completeTask_retrievedTaskIsComplete() = runTest {
        val task = Task("TASK 2", "TASK 2 DESC")
        tasksDao.insertTask(task)

        tasksDao.updateCompleted(task.id, true)
        val retrievedTask = tasksDao.getTaskById(task.id)
        assertThat(retrievedTask?.isCompleted, `is`(true))
    }
}