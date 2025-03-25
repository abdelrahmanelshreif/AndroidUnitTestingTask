package com.example.testing_day1.statistics

import com.example.testing_day1.data.Task

/**
 * Function that does some trivial computation. Used to showcase unit tests.
 */
internal fun getActiveAndCompletedStats(tasks: List<Task>?): StatsResult {
    val activePercent: Int
    val completePercent: Int

    if (!tasks.isNullOrEmpty()) {
        val totalTasks = tasks.size
        val numberOfActiveTasks = tasks.count { it.isActive }
        activePercent = 100 * numberOfActiveTasks / totalTasks
        completePercent = 100 * (totalTasks - numberOfActiveTasks) / totalTasks
    } else {
        activePercent = 0
        completePercent = 0
    }

    return StatsResult(
        activeTasksPercent = activePercent.toFloat(),
        completedTasksPercent = completePercent.toFloat()
    )
}

data class StatsResult(val activeTasksPercent: Float, val completedTasksPercent: Float)
