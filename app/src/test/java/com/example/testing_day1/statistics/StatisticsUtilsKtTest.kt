package com.example.testing_day1.statistics

import com.example.testing_day1.data.Task
import junit.framework.TestCase.assertEquals
import org.junit.Test

class StatisticsUtilsKtTest {

    @Test
    fun getActiveAndCompletedStats_noCompletedTasks() {
        val tasks = listOf(
            Task(isCompleted = false),
            Task(isCompleted = false),
            Task(isCompleted = false),
        )
        val result = getActiveAndCompletedStats(tasks)
        assertEquals(0f, result.completedTasksPercent)
    }


    @Test
    fun getActiveAndCompletedStats_2CompletedTasks3Active() {
        val tasks = listOf(
            Task(isCompleted = true),
            Task(isCompleted = true),
            Task(isCompleted = false),
            Task(isCompleted = false),
            Task(isCompleted = false)
        )

        val result = getActiveAndCompletedStats(tasks)
        assertEquals(40f, result.completedTasksPercent)
        assertEquals(60f, result.activeTasksPercent)

    }

    @Test
    fun getActiveAndCompletedStats_emptyList() {
        val tasks = emptyList<Task>()
        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)

    }

    @Test
    fun getActiveAndCompletedStats_nullParam() {
        val tasks = null
        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)

    }


}