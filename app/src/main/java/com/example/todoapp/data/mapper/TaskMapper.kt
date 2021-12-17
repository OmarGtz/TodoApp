package com.example.todoapp.data.mapper

import com.example.todoapp.data.room.Task
import com.example.todoapp.domain.TaskDomain
import com.example.todoapp.presentation.task.model.TaskItem
import com.example.todoapp.presentation.task.model.TasksView

/**
 * TaskMapper
 *
 * @author (c) 2021, UVI TECH SAPI De CV, KAVAK
 */
object TaskMapper {

    fun toDomain(input: Task): TaskDomain {
        return TaskDomain(
            input.title,
            input.description,
            input.completed,
            input.id
        )
    }

    fun toPresentation(input: List<TaskDomain>): TasksView {
        val completedTasks = mutableListOf<TaskItem>()
        val activeTasks = mutableListOf<TaskItem>()
        for (task in input) {
            if (task.isActive) {
                activeTasks.add(TaskItem(task.title, task.description, task.completed, task.id))
            } else {
                completedTasks.add(TaskItem(task.title, task.description, task.completed, task.id))
            }
        }
        return TasksView(completedTasks, activeTasks)
    }

}