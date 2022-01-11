package com.example.todoapp.domain.mapper

import com.example.todoapp.domain.model.TaskDomain
import com.example.todoapp.presentation.task.model.TaskItem
import com.example.todoapp.presentation.task.model.TasksView

/**
 * TaskDomainExt
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */

fun List<TaskDomain>.toPresentation(): TasksView {
    val completedTasks = filter { it.completed }
    val activeTasks = filter { it.isActive }
    return TasksView(
        completedTasks.map { it.toItem() },
        activeTasks.map { it.toItem() }
    )
}

fun TaskDomain.toItem(): TaskItem {
    return TaskItem(title, description, completed, id)
}