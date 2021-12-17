package com.example.todoapp.presentation.task.model

/**
 * TaskView
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
data class TasksView(
    val completedTasks: List<TaskItem>,
    val activeTasks: List<TaskItem>
) {
    val completedIsNotEmpty: Boolean
    get() = completedTasks.isNotEmpty()

    val activeIsNotEmpty: Boolean
    get() = activeTasks.isNotEmpty()

}

data class TaskItem(
    val title: String,
    val description: String,
    val completed: Boolean,
    val id: String
)
